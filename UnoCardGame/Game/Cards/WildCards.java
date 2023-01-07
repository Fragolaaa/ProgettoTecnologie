package Game.Cards;

public class WildCards extends Card{ // +2, cambio giro, stop
    public int color; //0 = red, 1 = blue, 2 = yellow, 3 = green
    public int type; //0 = +2, 1 = reverse, 2 = skip


    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }
}