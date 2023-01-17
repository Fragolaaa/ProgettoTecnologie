package Game.Cards;

public class NumberCard extends Card{
    public static final String className = "NumberCard";
    protected final String marker;
    
    public NumberCard(int color, int value) {
        super(color);
        this.value = value;
        marker = String.valueOf(value);
    }

    private final int value;

    @Override
    public String toString() {
        return className + " color: " + getColor() + " value: " + value;
    }

    @Override
    public String drawCLI() {
        String string = "";
        int i = 0;
        for (String row : outline) {
            i++;
            if(i == 4) row = row.substring(0, 6 - Integer.toString(value).length()) + Integer.toString(value) + row.substring(6);
            //if(i == 5 && getIsUsed()) row = row.substring(0, 7 - colorName[color].length()) + colorName[color] + row.substring(7);
            if(i == 5) row = row.substring(0, 7 - colorName[color].length()) + colorName[color] + row.substring(7);
            string += row + "\n";
        }
        return string;
    }

    public static String getClassname() {
        return className;
    }

    public String getMarker() {
        return marker;
    }

    public int getValue() {
        return value;
    }
}
