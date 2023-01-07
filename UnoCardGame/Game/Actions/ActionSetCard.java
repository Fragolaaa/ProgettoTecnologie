package Game.Actions;
import Game.Cards.Card;

public class ActionSetCard extends Action{
    public Card card;

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.visit(this);
    }
    
}
