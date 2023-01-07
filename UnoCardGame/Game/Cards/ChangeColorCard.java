package Game.Cards;

public class ChangeColorCard extends Card{
    public int color = -1; //colore selezionato
    public int type; //0 = +4,  1 = cambio colore;
    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }

}
