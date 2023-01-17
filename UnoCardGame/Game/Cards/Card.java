package Game.Cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Card implements Serializable{
    protected final int color; //0 - 3
    protected final String[] colorName = {"Red", "Grn", "Blu", "Ylw"};
    protected final String marker = "";
    protected static final int cardH = 7;
    protected final String[] outline = {" _________ ",
    "|         |",
    "|         |",
    "|         |",
    "|         |",
    "|         |",
    " --------- "};


    public int getColor(){
        return color;
    }
    public Card(int color){
        this.color = color;
    }
    public Card(){
        color = -1;
    }
    public String drawCLI() {
        String string = "";
        String[] render = generateDraw();
        for (String row : render) string += row + "\n";
        return string;
    }

    protected String[] generateDraw(){
        String[] res = new String[outline.length];
        int i = 0;
        for (String row : outline) {
            if(i == 4) row = row.substring(0, 6 - marker.length()) + marker + row.substring(6);
            //if(i == 5 && getIsUsed()) row = row.substring(0, 7 - colorName[color].length()) + colorName[color] + row.substring(7);
            if(i == 5) row = row.substring(0, 7 - colorName[color].length()) + colorName[color] + row.substring(7);
            res[i] = row;
            i++;
        }
        return res;
    }

    public static String drawCLI(Collection<Card> cards){
        String string = "";
        ArrayList<String[]> draws = new ArrayList<>();
        for (Card card : cards) {
            if(card instanceof NumberCard)
                draws.add(((NumberCard)card).generateDraw());
            else
                draws.add(card.generateDraw());
        }
        for(int i = 0; i < cardH; i++){
            for (String[] draw : draws) {
                string += draw[i] + "  ";
            }
            string += "\n";
        }
        return string;
    }
}
