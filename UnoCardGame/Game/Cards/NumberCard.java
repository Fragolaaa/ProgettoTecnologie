package Game.Cards;

public class NumberCard extends Card{ // 0 - 9
    public NumberCard(int value, int color) {
        this.value = value;
        this.color = color;
    }

    public int value; //0 - 9
    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }


}
