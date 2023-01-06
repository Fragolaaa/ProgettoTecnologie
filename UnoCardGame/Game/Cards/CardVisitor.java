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
        //fai qualcosa
    }

    public void visit(NumberCard numberCard) {

    }

    public void visit(WildCards WildCards) {
    }
}