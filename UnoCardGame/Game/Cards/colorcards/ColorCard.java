package Game.Cards.colorcards;

import Game.Cards.Card;

public abstract class ColorCard extends Card{
    public ColorCard(){

    }

    protected boolean isUsed = false; //indica se la carta e' a terra (true) o in mano (false)
    protected int color = -1; //-1 se il colore e' ancora jolly

    public boolean getIsUsed(){
        return isUsed;
    }

    public void use(int color){
        if(!isUsed){
            this.color = color;
            isUsed = true;
        }
    }

    public int getColor(){
        return color;
    }
}
