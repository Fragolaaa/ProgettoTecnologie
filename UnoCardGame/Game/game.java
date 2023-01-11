package Game;

import java.util.ArrayList;

import Game.Cards.Card;
import Game.Cards.CardVisitor;
import Game.Cards.NumberCard;
import Game.Cards.WildCards;
import Game.Actions.*;
import Game.Notifies.*;

public class Game extends Thread{
    private final Deck deck = new Deck();
    private ArrayList<Card> downCards = new ArrayList<>();
    private final int numberOfStartingCards = 7;

    private ArrayList<PlayerState> players= new ArrayList<>();
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
    

    }
    private Card getLastCard(){ //prendo l'ultima carta che ho buttato sul tavolo
        return downCards.get(downCards.size()-1);
    }

    private boolean checkCards(Card card1, Card card2){ //controllo se è possibile mettere a terra la carta
    
        if(card1.color == card2.color || card1.color == -1){
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
    
    public void setCard(Client client, Card card) { //imposta la nuova carta
        //controllo validità carta
        Card lastCard= getLastCard();
        if(checkCards(card, lastCard)){
            card.accept(new CardVisitor(this));
            
            downCards.add(card); //l'ultima carta inserita e' sempre in posizione card.size() - 1
            
            NotifyPlayerHandChanged notifyPlayerHandChanged = new NotifyPlayerHandChanged();
            notifyPlayerHandChanged.howManyCardsChanged = -1;
            updateAllPlayers(notifyPlayerHandChanged, client);
            NotifyCardChanged notifyCardChanged = new NotifyCardChanged();
            notifyCardChanged.card = card;
            updateAllPlayers(notifyCardChanged);
            
        }
           
        else
            updatePlayer(new NotifyInvalidMove("Invalid card"), client);
    

         //controllare se posso settare la carta
        //posso settare se: colore/numero uguale a quella prima o una cambio colore
        
        
      
        //mandare la risposta al client se puo' o non puo' settare la carta
       

        
    }
    public void nextPlayer(int currentPlayer,ArrayList<PlayerState> players) {
      
    
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
}