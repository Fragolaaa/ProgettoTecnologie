package Game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import Game.Cards.*;

public class Game extends Thread{
    private final int MAX_CARDS = 7;
    private HashMap<String, Player> players = new HashMap<>();
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
            sendToPlayer(player.getValue().getClientSocket(), player.getValue().getHand());
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

    private void sendMessageBroadcast(Object message){
        for (Map.Entry<String, Player> player : players.entrySet()) {
            player.getValue().getClientSocket().sendMessageToClient(message); //ogni giocatore pesca 7 carte
        }
    }

    private void sendToPlayer(Client clientSocket, Object message) {
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
        sendToPlayer(currentPlayer, "It's your turn");
    
    }

    public void reverse(){

    }

    public void drawCards(int NumCardsToDraw,Player currentPlayer){
        ArrayList<Card> cardsToDraw = deck.popCard(NumCardsToDraw); //creo arraylist di n carte prese dal deck
        currentPlayer.addToHand(cardsToDraw);

    }

    private Card getLastCard(){ //prendo l'ultima carta che ho buttato sul tavolo
    return downCards.get(downCards.size()-1);
    }

}
