package Game.Cards;

import java.io.Serializable;

public abstract class Card implements Serializable{
    public abstract void accept(CardVisitor visitor);
    public int color; //0 rosso -1 verde -2 giallo -3 blu 
}
