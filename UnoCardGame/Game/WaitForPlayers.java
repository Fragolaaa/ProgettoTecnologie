package Game;

public class WaitForPlayers extends Thread{

    private final Game game;
    private boolean waiting = true;
    private final int MAX_PLAYERS = 4;
    private final int MIN_PLAYERS = 2;

    public WaitForPlayers(Game game){
        this.game = game;
    }

    @Override
    public void run() {
        while (waiting && game.countPlayers() <= MAX_PLAYERS) {
            if(game.countPlayers() >= MIN_PLAYERS) waiting = false;
        }
    }
}
