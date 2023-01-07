package Game;

//il server gestisce i deck di tutti i giocatori

import java.util.*;

import Game.Cards.*;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();

    public Deck() {
      

        // deck ha tutte le carte da 0 a 9 per ogni colore

        for (int i = 0; i < 4; i++)
            for (int j = 0; j <= 9; j++)
                deck.add(new NumberCard(j,i));

        // deck ha tutte le carte +2
        for (int i = 0; i < 4; i++) {
            WildCardDrawTwo wd = new WildCardDrawTwo();
            wd.color = i;
            deck.add(wd);
        }
        // deck ha tutte le carte skip
        for (int i = 0; i < 4; i++) {
            WildCardSkip wd = new WildCardSkip();
            wd.color = i;
            deck.add(wd);
        }
        // deck ha tutte le carte reverse
        for (int i = 0; i < 4; i++) {
            WildCardReverse wd = new WildCardReverse();
            wd.color = i;
            deck.add(wd);
        }

        // deck ha tutte le carte cambio colore
        // deck ha tutte le carte +4
        for(int i=0;i<2;i++){
            deck.add(new ChangeColorCardDrawFour());
            deck.add(new ChangeColorCard());
        }


        // deck completo

        // stampo il deck
        for (Card card : deck) {
           System.out.println(card + "\t" + card.color);

        }
        Card card = deck.get(2);
        
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card getTopCard() {
        Card topCard = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);
        return topCard;
    }
}
