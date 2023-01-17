package Game;
import java.util.ArrayList;
import java.util.Collections;

import Game.Cards.*;
import Game.Cards.wildcards.*;
import Game.Cards.colorcards.*;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();

    public Deck(){
        for(int i = 0; i < 4; i++){//per ogni colore

            deck.add(new NumberCard(i, 0)); //1 carta per lo 0
            for(int j = 1; j <= 9; j++){ //2 carte per ogni numero da 1 a 9
                deck.add(new NumberCard(i, j));
                deck.add(new NumberCard(i, j));
            }
            for(int j = 0; j<2; j++){//2 wildcard per ogni tipo
                deck.add(new WildCardDrawTwo(j));   
                deck.add(new WildCardReverse(j));
                deck.add(new WildCardSkipTurn(j));
            }
            deck.add(new ColorChangerCard());//1 carta cambia colore
            deck.add(new ColorChangerCardDrawFour());//1 carta pesca quattro
        }

        shuffle();
        //for (Card card : deck) System.out.println(card.toString());
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public ArrayList<Card> popCard(int quantity){ //rimuove n carte dal mazzo
        ArrayList<Card> cards = new ArrayList<>();
        if(deck.size() - quantity >= 0 && quantity > 0){
            for(int i = 0; i < quantity; i++){
                cards.add(deck.get(deck.size() - 1));
                deck.remove(deck.size() - 1);
            }
        }
        return cards; //ritorna vuoto se le carte non sono abbastanza
    }
}
