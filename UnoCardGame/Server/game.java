import java.util.ArrayList;
//questa classe gestisce il gioco, i vari cambio giro etc;
//to do: IL SERVER DEVE GESTIRE SOLO LA COMUNICAZIONE TRA I CLIENT E DIRGLI COSA FARE! qui troppa roba!!!
public class game {
   
    private giocatori[];
    public enum Direction { FORWARDS, BACKWARDS };

    private GameState state;//oggetto per rappresentare lo stato del gioco in ogni momento

    //variabili che caratterizzano lo stato di gioco

    Direction direction;//turno
    int currPlayer;//giocatore di turno
    UnoPlayer.Color calledColor;//colore della carta 
    UnoPlayer.Color mostRecentColorCalled[];//colore dell'ultima carta giocata

    /**
     * Costruttore principale 
     * @scoreboard segnapunti con nomi dei giocatori in ordine
     * @param playerClassList[] vettore di stringhe contenente per implementazione interfaccia
     */
    public Game() {
        direction = Direction.FORWARDS;
        currPlayer = new java.util.Random();//.nextInt(scoreboard.getNumPlayers());
        calledColor = UnoPlayer.Color.NONE;
    }

    /**
     * ritorna il giocatore successivo 
     * in caso di direzione in avanti
     * giocatore precedente 
     * in caso di direzione indietro(non è avanti)
     */
    public int getNextPlayer() { //Tania: questo è ok
        if (direction == Direction.FORWARDS) {//se turno va avanti
            return (currPlayer + 1); //% scoreboard.getNumPlayers();
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
    void advanceToNextPlayer() { //Tania: ok
        currPlayer = getNextPlayer();
    }

    /**
     * cambia direzione gioco(cambio turno)
     */
    void reverseDirection() { //Tania: ok
        if (direction == Direction.FORWARDS) {
            direction = Direction.BACKWARDS;
        }
        else {
            direction = Direction.FORWARDS;
        }
    }
}
