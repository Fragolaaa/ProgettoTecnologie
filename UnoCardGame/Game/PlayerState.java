package Game;

import java.io.Serializable;
import java.util.ArrayList;

import Game.Cards.*;
import Game.Actions.*;
import Game.Notifies.*;

//salva lo stato di ogni client, aggiunge carte al suo deck
public class PlayerState implements Serializable{
    private ArrayList<Card> hand = new ArrayList<>();
    private boolean isPlaying = false;
    public final Client client;
    public final int id;

    public PlayerState(Client client, int id){
        this.client = client;
        this.id = id;
    }

    public void addCardToHand(Card card){
        hand.add(card);
    }

    public void notifySelfState() {
        NotifyPlayerState notifyPlayerState = new NotifyPlayerState();
        notifyPlayerState.playerState = this;
        client.sendNotify(notifyPlayerState);
    }
}