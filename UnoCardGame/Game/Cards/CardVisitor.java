package Game.Cards;

import Game.Client;
import Game.Game;

public class CardVisitor {
    private final Client client;
    private final Game game;

    public CardVisitor(Client client, Game game) {
        this.client = client;
        this.game = game;
    }

    public void visit(ChangeColorCard ChangeColorCard){
        //il nuovo colore rispettare è....
        //eventuale pescata di 4 carte
        if(ChangeColorCard.type=0){
            // for(int i=0; i<4;i++){ //fai pescare 4 carte al prossimo client, fai scegliere al client che ha buttato la carta il prossimo colore
            //     client.addCardToHand(game.deck.getTopCard());
            // }
        }
        else{

        }
    }

    public void visit(NumberCard numberCard) {
        //nuovo colore/numero da rispettare
    }

    public void visit(WildCards WildCards) {
        // vedi se +2, skip o reverse

    }
}