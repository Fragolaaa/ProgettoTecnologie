package Game.Actions;

import java.io.Serializable;

public abstract class Action implements Serializable{
    private static final long serialVersionUID = -8393284750293847561L;
    public abstract void accept(ActionVisitor visitor);
}


