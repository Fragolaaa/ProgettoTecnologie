package Game.actions;

public abstract class Action {
    public abstract void accept(ActionVisitor visitor);
}
