package Game.Actions;
import java.io.Serializable;

import Game.Cards.Card;

public class ActionSetCard extends Action implements Serializable{
    public Card card;

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.visit(this);
    }
    
}
