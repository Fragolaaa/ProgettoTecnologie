package Game.actions;

import Game.cards.Card;

public class ActionSetCard extends Action{
    public Card card;

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.visit(this);
    }
    
}
