package Game.Cards;

public class WildCardSkip extends WildCards{

    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }
}
