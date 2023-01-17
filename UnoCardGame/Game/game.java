package Game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Game.Cards.*;

public class Game extends Thread{
    private final int MAX_CARDS = 7;
    private HashMap<String, Player> players = new HashMap<>();
    private Deck deck = new Deck();
    private ArrayList<Card> downCards = new ArrayList<>();
    private boolean waiting = true;
    private boolean playing = false;

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

    public void reverseCard(){
        for (int i = 0; i < players.size() / 2; i++) {
            Player temp = players.get(i);
            players.set(i, players.get(players.size() - i - 1));
            players.set(players.size() - i - 1, temp);
        }
    }
}
