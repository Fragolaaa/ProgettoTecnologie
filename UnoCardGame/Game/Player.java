package Game;

import java.util.ArrayList;

import Game.Cards.*;

public class Player {
    private ArrayList<Card> hand = new ArrayList<>();
    private final Client clientSocket;
    private String name;

    public Player(Client clientSocket, String name) {
        this.clientSocket = clientSocket;
        this.name = name;
    }

    public void addToHand(ArrayList<Card> cards){
        hand.addAll(cards);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public Client getClientSocket() {
        return clientSocket;
    }

    @Override
    public String toString(){
        String handString = "";
        for (Card card : hand) {
            handString += card.drawCLI() + "\n\n";
        }
        return clientSocket.toString() + " Name: " + name + " Hand: " + handString;
    }
}