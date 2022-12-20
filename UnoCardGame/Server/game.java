import java.util.ArrayList;
//to do: IL SERVER DEVE GESTIRE SOLO LA COMUNICAZIONE TRA I CLIENT E DIRGLI COSA FARE! qui troppa roba!!!
public class game {
   
    static final int handSize = 7;
    public enum Direction { FORWARDS, BACKWARDS };

    private GameState state;//oggetto per rappresentare lo stato del gioco in ogni momento

    //variabili che caratterizzano lo stato di gioco
    Deck deck;//mazzo di carte
    Hand h[];//carte in mano del giocatore
    Card upCard;//carta
    Direction direction;
    int currPlayer;
    UnoPlayer.Color calledColor;//colore della carta 
    Scoreboard scoreboard;//segnapunti
    UnoPlayer.Color mostRecentColorCalled[];//colore dell'ultima carta giocata

    /**
     * Costruttore principale 
     * @param scoreboard segnapunti con nomi dei giocatori in ordine
     * @param playerClassList[] vettore di stringhe contenente per implementazione interfaccia
     */
    public Game(Scoreboard scoreboard, ArrayList<String> playerClassList) {
        this.scoreboard = scoreboard;
        deck = new Deck();
        h = new Hand[scoreboard.getNumPlayers()];//numero di giocatori 2-4
        mostRecentColorCalled = new UnoPlayer.Color[scoreboard.getNumPlayers()];
        try {
            for (int i=0; i<scoreboard.getNumPlayers(); i++) {
                h[i] = new Hand(playerClassList.get(i), scoreboard.getPlayerList()[i]);
                for (int j=0; j<INIT_HAND_SIZE; j++) {
                    h[i].addCard(deck.draw());//pesca una carta
                }
            }
            upCard = deck.draw();
            while (upCard.followedByCall()) {
                deck.discard(upCard);
                upCard = deck.draw();
            }
        }
        catch (Exception e) {
            System.out.println("Can't deal initial hands!");
            System.exit(1);
        }
        direction = Direction.FORWARDS;
        currPlayer =
            new java.util.Random().nextInt(scoreboard.getNumPlayers());
        calledColor = UnoPlayer.Color.NONE;
    }

    private void printState() {
        for (int i=0; i<scoreboard.getNumPlayers(); i++) {
            System.out.println("Hand #" + i + ": " + h[i]);
        }
    }

    /**
     * ritorna il giocatore successivo 
     * in caso di direzione in avanti
     * giocatore precedente 
     * in caso di direzione indietro(non è avanti)
     */
    public int getNextPlayer() {
        if (direction == Direction.FORWARDS) {//se turno va avanti
            return (currPlayer + 1) % scoreboard.getNumPlayers();
        }
        else {
            if (currPlayer == 0) {
                return scoreboard.getNumPlayers() - 1;
            }
            else {
                return currPlayer - 1;
            }
        }
    }

    /**
     * avanza al prossimo giocatore
     */
    void advanceToNextPlayer() {
        currPlayer = getNextPlayer();
    }

    /**
     * cambia direzione gioco(cambio turno)
     */
    void reverseDirection() {
        if (direction == Direction.FORWARDS) {
            direction = Direction.BACKWARDS;
        }
        else {
            direction = Direction.FORWARDS;
        }
    }
}
