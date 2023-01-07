package Game.Cards;

public abstract class Card{
    public abstract void accept(CardVisitor visitor);
    public int color;
}
