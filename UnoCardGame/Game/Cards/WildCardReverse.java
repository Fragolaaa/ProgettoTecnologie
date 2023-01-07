package Game.Cards;

public class WildCardReverse extends WildCards{
    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }
}
