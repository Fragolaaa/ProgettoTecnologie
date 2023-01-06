package Game.cards;

public abstract class Card{
    public abstract void accept(CardVisitor visitor);
}
