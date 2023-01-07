package Game.Cards;

public abstract class WildCardReverse extends WildCards{
    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }
}
