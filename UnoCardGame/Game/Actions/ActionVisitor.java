package Game.Actions;
import Game.Client;
import Game.Game;

public class ActionVisitor {
    private final Client client;
    private final Game game;

    public ActionVisitor(Client client, Game game) {
        this.client = client;
        this.game = game;
    }

    public void visit(ActionSetCard actionSetCard) {
        game.setCard(client, actionSetCard.card);
    }

    public void visit(ShoutUno shoutUno) {
    }
    
}