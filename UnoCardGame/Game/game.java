package Game;

import java.util.ArrayList;

import Game.Cards.Card;
import Game.Actions.*;
import Game.Notifies.*;

public class Game extends Thread{
    private final Deck deck = new Deck();
    private ArrayList<Card> downCards;
    private final int numberOfStartingCards = 7;

    private ArrayList<PlayerState> players;
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
    
    public void setCard(Client client, Card card) {
        String msg;
       
         //controllare se posso settare la carta
        //posso settare se: colore/numero uguale a quella prima o una cambio colore
        if(card.color == downCards[downCards.size()-1].color || 
        card.type == downCards[downCards.size()-1].type ||
         card.color == -1){
                msg="Action successfully saved";
                //aggiungo la carta
            downCards.add(card); //l'ultima carta inserita e' sempre in posizione card.size() - 1
            
            NotifyPlayerHandChanged notifyPlayerHandChanged = new NotifyPlayerHandChanged();
            notifyPlayerHandChanged.howManyCardsChanged = -1;
            updateAllPlayers(notifyPlayerHandChanged, client);
            NotifyCardChanged notifyCardChanged = new NotifyCardChanged();
            notifyCardChanged.card = card;
            updateAllPlayers(notifyCardChanged);
            
        }
        else {
            msg="You can't place this card, please try with another one...";
        }

        //mandare la risposta al client se puo' o non puo' settare la carta
        player.client.sendNotify(msg);

        
    }
    public void nextPlayer(int currentPlayer,ArrayList<PlayerState> players) {
        if(direction==0){

<<<<<<< HEAD
        }
        else if(direction==1){

        }
        
    }
=======


>>>>>>> 7a786d639475cd62faea936b32f49764f6fd0b74
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
}