package Game.Cards;

public abstract class WildCardSkip extends WildCards{

    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }
}
