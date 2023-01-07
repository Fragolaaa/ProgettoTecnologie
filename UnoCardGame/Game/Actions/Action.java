package Game.Actions;

public abstract class Action {
    public abstract void accept(ActionVisitor visitor);
}
