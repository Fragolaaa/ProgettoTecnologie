package Game.Network;

import java.io.Serializable;
import java.util.ArrayList;

import Game.Cards.Card;

public class Message implements Serializable{
    private String arg;
    private ArrayList<Card> cards;

    public Message(String arg, ArrayList<Card> cards){
        this.arg = arg == null ? "" : arg;
        this.cards = cards == null ? new ArrayList<Card>() : cards;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}