package Game;

import java.util.ArrayList;

import Game.Cards.Card;
import Game.Cards.CardVisitor;
import Game.Cards.ChangeColorCard;
import Game.Cards.ChangeColorCardDrawFour;
import Game.Cards.NumberCard;
import Game.Cards.WildCards;
import Game.Actions.*;
import Game.Notifies.*;

public class Game extends Thread{  //to do: gestione turni, avvisa prox utente che è il suo turno
    private final Deck deck = new Deck();
    private ArrayList<Card> downCards = new ArrayList<>();
    private final int numberOfStartingCards = 7;

    private ArrayList<PlayerState> players= new ArrayList<>();
    private Client currPlayer;
    private int direction=0; //0->forwards; 1->backwards;
    
    public Game(ArrayList<Client> clients){
        int id = 0;
        for (Client client : clients) {
            players.add(new PlayerState(client, id));
            client.setGame(this);
            client.start();
            id++;
        }
    }

    @Override
    public void run(){
        //tutti i client nel game ricevono le proprie carte
        //dai ad ogni player la sua carta (usa un for che toglie la prima carta del mazzo e la da al player)
        for(int i = 0; i < numberOfStartingCards; i++){
            for (PlayerState playerState : players) {
                playerState.addCardToHand(deck.getTopCard());
            }
        }

        for (PlayerState playerState : players) {
            playerState.notifySelfState();
        }
        
        //si dice chi inizia
        nextPlayer(null, players);

    }

    private Card getLastCard(){ //prendo l'ultima carta che ho buttato sul tavolo
        return downCards.get(downCards.size()-1);
    }

    private boolean checkCards(Card card1, Card card2){ //controllo se è possibile mettere a terra la carta

        //posso settare se: colore/numero uguale a quella prima o una cambio colore
        if(card1.color == card2.color || card1 instanceof ChangeColorCard || card1 instanceof ChangeColorCardDrawFour){
            //ok colore uguale/cambio colore
            return true;
        }
        else if(card1.getClass() == card2.getClass()){
            //controlla se numbercard

            if(card1 instanceof NumberCard){
                NumberCard nCard1 = (NumberCard)card1;
                NumberCard nCard2 = (NumberCard)card2;
                return nCard1.value == nCard2.value; 
            }
            else{
                //ok
                return true;
            }
    
        }
        else{
            //not ok
            return false;
        }
    }
    
    public boolean setCard(Client client, Card card) { //imposta la nuova carta
        //controllo validità carta
        Card lastCard= getLastCard();
        if(checkCards(card, lastCard)){
            card.accept(new CardVisitor(this, client));
            
            downCards.add(card); //l'ultima carta inserita e' sempre in posizione card.size() - 1
            
            NotifyPlayerHandChanged notifyPlayerHandChanged = new NotifyPlayerHandChanged();
            notifyPlayerHandChanged.howManyCardsChanged = -1;
            updateAllPlayers(notifyPlayerHandChanged, client);
            NotifyCardChanged notifyCardChanged = new NotifyCardChanged();
            notifyCardChanged.card = card;
            updateAllPlayers(notifyCardChanged);
            nextPlayer(client, players); //passo al prossimo giocatore
            return true;
            
        }
           
        else{
        
            updatePlayer(new NotifyInvalidMove("Invalid card"), client);
            return false;
        //mandare la risposta al client se puo' o non puo' settare la carta
        }
            

    }
    //per gestire i turni quando in modalità antioraria basta che giro il vettore...
    public void nextPlayer(Client cp,ArrayList<PlayerState> players) {
        //prendo il giocatore attuale, vado al prossimo
        int c=0;
        if(cp != null){ //se ho già iniziato il gioco
            for (PlayerState p : players) {
                    if(p.client.equals(cp)){
                        if(players.indexOf(p)==(players.size()-1))
                            c=0;
                        else
                            c=players.indexOf(p); 
                    } 
            }
        }

        currPlayer=players.get(c).client;
        //dico al prox giocatore che è il suo turno  (isPlaying del giocatore a true)
        NotifyTurn notifyTurn = new NotifyTurn();
        updatePlayer(notifyTurn, currPlayer);
    
    }

    /**
     * @param notify
     * @param client the client not to update
     */
    
    private void updateAllPlayers(Notify notify, Client client){ //updates all clients except one
        for (PlayerState player : players) {
            if(!player.client.equals(client)) player.client.sendNotify(notify);
        }
    }
    private void updateAllPlayers(Notify notify){ //updates all clients
        for (PlayerState player : players) {
            player.client.sendNotify(notify);
        }
    }

    private void updatePlayer(Notify notify, Client client){ //updates a single player
        client.sendNotify(notify);
    }

    public void nextDraw(int i) { //faccio pescare n carte al giocatore
        nextPlayer(currPlayer, players);
        for (PlayerState p : players) {
            if(p.client.equals(currPlayer)){
                for(int j=0;j<=i;j++){
                    p.addCardToHand(deck.getTopCard());
                }
            }
        }
    }

    public void reverse() { // rovescio l'arraylist
        for (int i = 0; i < players.size() / 2; i++) {
            PlayerState temp = players.get(i);
            players.set(i, players.get(players.size() - i - 1));
            players.set(players.size() - i - 1, temp);
        }
    }

    public void skipPlayer() {
        nextPlayer(currPlayer, players);
        NotifyDeniedTurn notifyDeniedTurn = new NotifyDeniedTurn();
        updatePlayer(notifyDeniedTurn, currPlayer);//the turn has been denied, player turn must be skipped

        nextPlayer(currPlayer, players);//next player
    }
}