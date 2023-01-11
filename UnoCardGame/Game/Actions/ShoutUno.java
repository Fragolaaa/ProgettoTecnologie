package Game.Actions;

public class ShoutUno extends Action{

    @Override
    public void accept(ActionVisitor visitor) {
            visitor.visit(this);
    }
 }
    
