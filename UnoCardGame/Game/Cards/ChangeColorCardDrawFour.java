package Game.Cards;

public class ChangeColorCardDrawFour extends ChangeColorCard{
    public ChangeColorCardDrawFour() {
        color=-1;
    }

    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }
    
}
