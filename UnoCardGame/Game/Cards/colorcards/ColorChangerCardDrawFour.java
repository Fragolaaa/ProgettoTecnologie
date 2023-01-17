package Game.Cards.colorcards;

import Game.Cards.Card;
public class ColorChangerCardDrawFour extends ColorCard{
    public static final String className = "ColorChangerCardDrawFour";
    protected final String marker = "+4";
    @Override
    public String toString() {
        return className + " color: " + getColor() + " is used: " + getIsUsed();
    }

    @Override
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
            if(i == 5) {
                String dummy = color == -1 ? "?" : colorName[color];
                row = row.substring(0, 7 - dummy.length()) + dummy + row.substring(7);
            }
            res[i] = row;
            i++;
        }
        return res;
    }
}
