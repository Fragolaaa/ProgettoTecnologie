package Game.Cards;

public class WildCardDrawTwo extends WildCards{
    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }
}
