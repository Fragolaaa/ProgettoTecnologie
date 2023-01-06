package Game;

import java.util.ArrayList;

import Game.cards.Card;
import Game.notifies.NotifyPlayerState;

//salva lo stato di ogni client, aggiunge carte al suo deck
public class PlayerState {
    private ArrayList<Card> hand;
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