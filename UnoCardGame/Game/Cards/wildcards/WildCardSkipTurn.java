package uno.server.cards.wildcards;

public class WildCardSkipTurn extends WildCard{
    public static final String className = "WildCardSkipTurn";
    protected final String marker = "@";
    public WildCardSkipTurn(int color) {
        super(color);
    }
    
    @Override
    public String toString() {
        return className + " color: " + getColor();
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
            if(i == 5) row = row.substring(0, 7 - colorName[color].length()) + colorName[color] + row.substring(7);
            res[i] = row;
            i++;
        }
        return res;
    }
}
