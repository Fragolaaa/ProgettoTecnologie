package Game;

import java.util.ArrayList;

import Game.cards.Card;
import Game.notifies.Notify;
import Game.notifies.NotifyCardChanged;
import Game.notifies.NotifyPlayerHandChanged;
import Game.notifies.NotifyPlayerState;

public class game extends Thread{
    private final Deck deck = new Deck();
    private ArrayList<Card> downCards;
    private final int numberOfStartingCards = 7;

    private ArrayList<PlayerState> players;

    public game(ArrayList<Client> clients){
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
        //si dice chi comincia
    }

    public void setCard(Client client, Card card) {
        downCards.add(card); //l'ultima carta inserita e' sempre in posizione card.size() - 1
        NotifyPlayerHandChanged notifyPlayerHandChanged = new NotifyPlayerHandChanged();
        notifyPlayerHandChanged.howManyCardsChanged = -1;
        updateAllPlayers(notifyPlayerHandChanged, client);
        NotifyCardChanged notifyCardChanged = new NotifyCardChanged();
        notifyCardChanged.card = card;
        updateAllPlayers(notifyCardChanged);
        //controllare se posso settare la carta
        //mandare la risposta al client se puo' o non puo' settare la carta
    }

    /**
     * @param notify
     * @param client the client not to upldate
     */
    private void updateAllPlayers(Notify notify, Client client){
        for (PlayerState player : players) {
            if(!player.client.equals(client)) player.client.sendNotify(notify);
        }
    }
    private void updateAllPlayers(Notify notify){
        for (PlayerState player : players) {
            player.client.sendNotify(notify);
        }
    }
}