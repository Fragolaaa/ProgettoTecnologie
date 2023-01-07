package Game;

//il server gestisce i deck di tutti i giocatori

import java.util.*;

import Game.Cards.*;

public class Deck {
    private ArrayList<Card> deck;

    public Deck(){
        NumberCard blue1 = new NumberCard();
        blue1.color = 1;
        blue1.value = 1;

    //deck ha tutte le carte da 0 a 9 per ogni colore

        for(int i = 0; i < 4; i++){
            for(int j = 0; j <= 9; j++){
                NumberCard nb = new NumberCard();
                nb.color = i;
                nb.value = j;
                deck.add(nb);
            }
        }
       
    //deck ha tutte le carte +2
    for(int i = 0; i < 4; i++){
        WildCardDrawTwo wd = new WildCardDrawTwo();
        wd.color = i;
        deck.add(wd);
    }
    //deck ha tutte le carte skip
    for(int i = 0; i < 4; i++){
        WildCardSkip wd = new WildCardSkip();
        wd.color = i;
        deck.add(wd);
}
    //deck ha tutte le carte reverse
    for(int i = 0; i < 4; i++){
        WildCardReverse wd = new WildCardReverse();
        wd.color = i;
        deck.add(wd);
}
    

    //deck ha tutte le carte cambio colore e +4

     for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2; j++){  //0->+4; 1->cambio colore
                ChangeColorCard cg = new ChangeColorCard();
                cg.type = j;
                deck.add(cg);
            }
        }


//deck completo

        //stampo il deck
        for (Card card : deck) {
            card.toString();
        }
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public Card getTopCard() {
        Card topCard = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);
        return topCard;
    }
}
