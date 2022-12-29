package Server;

public class table {
    private int[][] board = null;

    private String turn = "";
    
    public table(/*...*/) {
        
    }
    

    
    public String getTurn() {
        return turn;
    }
    
    public void changeTurns() {
        if (turn.equals(player1)) {
            turn = player2;
        } else if (turn.equals(player2)) {
            turn = player1;
        } else {
            System.err.println("Error: turn is messed up");
        }
    }
}