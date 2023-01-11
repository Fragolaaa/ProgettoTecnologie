package Game.Actions;

import java.io.Serializable;

public abstract class Action implements Serializable{
    
    public abstract void accept(ActionVisitor visitor);
}


