package Game.cards;

import Game.Client;
import Game.Game;

public class CardVisitor {
    private final Client client;
    private final Game game;

    public CardVisitor(Client client, Game game) {
        this.client = client;
        this.game = game;
    }

    public void visit(ColorChangerCard colorChangerCard){
        //do something with colorChangedCard
    }

    public void visit(NumberCard numberCard) {

    }

    public void visit(SpecialCard specialCard) {
    }
}