package Game;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import Game.Cards.*;
import Game.Cards.colorcards.ColorChangerCard;
import Game.Cards.colorcards.ColorChangerCardDrawFour;
import Game.Cards.wildcards.WildCardDrawTwo;
import Game.Cards.wildcards.WildCardReverse;
import Game.Cards.wildcards.WildCardSkipTurn;
import Game.Network.Message;

public class Game extends Thread{
    private final int MAX_CARDS = 7;
    private LinkedHashMap<String, Player> players = new LinkedHashMap();
    private Deck deck = new Deck();
    private ArrayList<Card> downCards = new ArrayList<>();
    private boolean waiting = true;
    private boolean playing = false;
    private Client currentPlayer;
    public Game(){ //game starts

    }

    @Override
    public void run() {
        //waitForPlayers();
        while(playing);
    }

    public void start(){
        downCards.addAll(deck.popCard(1)); //mette una carta sul tavolo per iniziare
        for (Map.Entry<String, Player> player : players.entrySet()) {
            player.getValue().addToHand(deck.popCard(MAX_CARDS)); //ogni giocatore pesca 7 carte
            sendToPlayer(player.getValue().getClientSocket(), new Message("SETHAND", player.getValue().getHand()));
        }
        playing = true;
    }

    public String addPlayer(String ID, Client client, String name){
        if(waiting) {
            Player player = players.put(ID, new Player(client, name));
            String message = player == null ? "OK;" + name + " joined the lobby" : "ERROR;" + name + " already in this lobby";
            System.out.println(message);
            return message;
            //put torna null se la chiave non e' mai stata istanziata prima, altrimenti
            //torna il valore precedente
        }
        else {
            System.out.println("The lobby is full"); //potrebbe essere mandato come messaggio al client
            return "ERROR;The lobby is full";
        }
    }

    private void waitForPlayers() {
        WaitForPlayers wait = new WaitForPlayers(this);
        wait.start();
        try {
            wait.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waiting = false;
        start();
    }

    public int countPlayers() {
        return players.size();
    }

    private void sendMessageBroadcast(Message message){
        for (Map.Entry<String, Player> player : players.entrySet()) {
            player.getValue().getClientSocket().sendMessageToClient(message); //ogni giocatore pesca 7 carte
        }
    }

    private void sendToPlayer(Client clientSocket, Message message) {
        clientSocket.sendMessageToClient(message);
    }

    public void nextPlayer(Client cp,LinkedHashMap<String, Player> players) {
        //prendo il giocatore attuale, vado al prossimo
        int c=0;
        int pos=0;
        if(cp != null){ //se ho già iniziato il gioco
            for (HashMap.Entry<String, Player> p : players.entrySet()) {
                    if(p.getValue().getClientSocket().equals(cp)){
                        if(players.get( (players.keySet().toArray())[pos] )==players.get( (players.keySet().toArray())[players.size()-1]))
                            c=0;
                        else
                            c=pos;
                        // if(players.indexOf(p)==(players.size()-1))
                        //     c=0;
                        // else
                        //     c=players.indexOf(p); 
                    } 
                pos++;
            }
        }

        currentPlayer=players.get( (players.keySet().toArray())[c] ).getClientSocket();
        //dico al prox giocatore che è il suo turno 
        sendToPlayer(currentPlayer, new Message("It's your turn to play!", null));
    
    }

    public void reverse() { // rovescio la linkedHashMap
        TreeMap<String,Player> tmap = new TreeMap<>(players);
        players.clear();
        players.putAll(tmap.descendingMap());
        // for (int i = 0; i < players.size() / 2; i++) {
        //     Client temp = players.get(i);
        //     players.set(i, players.get(players.size() - i - 1));
        //     players.set(players.size() - i - 1, temp);
        // }
    }


    public void skipPlayer() {
        nextPlayer(currentPlayer, players);
        sendToPlayer(currentPlayer, new Message("Your turn has been denied.", null));//the turn has been denied, player turn must be skipped
        nextPlayer(currentPlayer, players);//next player
    }



    public void drawCards(int NumCardsToDraw,Client currentPlayer){
        ArrayList<Card> cardsToDraw = deck.popCard(NumCardsToDraw); //creo arraylist di n carte prese dal deck
        for (HashMap.Entry<String, Player> p : players.entrySet()) {
            if(p.getValue().getClientSocket().equals(currentPlayer))
                p.getValue().addToHand(cardsToDraw);
        }

    }

    private Card getLastCard(){ //prendo l'ultima carta che ho buttato sul tavolo
    return downCards.get(downCards.size()-1);
    }
    
    private boolean checkCards(Card card1, Card card2){ //controllo se è possibile mettere a terra la carta

        //posso settare se: colore/numero uguale a quella prima o una cambio colore
        if(card1.getColor() == card2.getColor() || card1 instanceof ColorChangerCard || card1 instanceof ColorChangerCardDrawFour){
            //ok colore uguale/cambio colore
            return true;
        }
        else if(card1.getClass() == card2.getClass()){
            //controlla se numbercard

            if(card1 instanceof NumberCard){
                NumberCard nCard1 = (NumberCard)card1;
                NumberCard nCard2 = (NumberCard)card2;
                return nCard1.getValue() == nCard2.getValue(); 
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
        Card lastCard = getLastCard();
        if(checkCards(card, lastCard)){
            // card.accept(new CardVisitor(this, client));
            
            //l'ultima carta inserita e' sempre in posizione card.size() - 1
           
            // NotifyPlayerHandChanged notifyPlayerHandChanged = new NotifyPlayerHandChanged();
            // notifyPlayerHandChanged.howManyCardsChanged = -1;
            // updateAllPlayers(notifyPlayerHandChanged, client);
            // NotifyCardChanged notifyCardChanged = new NotifyCardChanged();
            // notifyCardChanged.card = card;
            // updateAllPlayers(notifyCardChanged);
            // nextPlayer(client, players); //passo al prossimo giocatore
            // return true;
            cardManager(card);//gli passo la carta
            sendMessageBroadcast(new Message(client+" just put down a card, next player's turn will begin soon", null));
            nextPlayer(currentPlayer,players);
            cardManager(card);//gli passo la carta
            return true;
        }
           
        else{
        
            sendToPlayer(currentPlayer, new Message("Invalid move!", null));
            return false;
        //mandare la risposta al client se puo' o non puo' settare la carta
        }
            

    }

    public void cardManager(Card card){
        downCards.add(card);
        if(card instanceof NumberCard){
            //nulla, metti la carta e basta
        }
        else if(card instanceof ColorChangerCard){
            //colore già selezionato
        }
        else if(card instanceof ColorChangerCardDrawFour){
            nextPlayer(currentPlayer, players); //passo al prossimo giocatore
            drawCards(4, currentPlayer); //pesco 4 
        }
        else if(card instanceof WildCardDrawTwo){
            nextPlayer(currentPlayer, players); //passo al prossimo giocatore
            drawCards(2, currentPlayer); //pesco 2
        }
        else if(card instanceof WildCardReverse){
            reverse();
            nextPlayer(currentPlayer, players);
        }
        else if(card instanceof WildCardSkipTurn){
            skipPlayer();
        }

    }

}
