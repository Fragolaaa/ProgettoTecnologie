package Game.Cards;

public class ChangeColorCard extends Card{
   
    public ChangeColorCard() {
        color=-1;
    }

    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }

}
