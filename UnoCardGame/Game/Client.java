//gestisce quel che deve fare il client 

package Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;


import Game.Actions.*;
import Game.Notifies.*;

public class Client extends Thread implements Serializable{  //ogni client è un thread

    public final int PORT;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final Socket socket;
    private String nickname; //to do: impostare cose con nickname
    Game game;

    public boolean isListening = false;


    public Client(int PORT, ObjectOutputStream outputStream, ObjectInputStream inputStream, Socket socket) {
        this.PORT = PORT;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.socket = socket;
        isListening = true;
    }

    @Override
    public void run(){
        //manda un msg al client vuoto
        //se non risponde listening = false
        while(isListening){
            Action action = null;

            try {
                action = (Action) inputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

            action.accept(new ActionVisitor(this, game));
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void sendNotify(Notify notify) {
        try {
            outputStream.writeObject(notify);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
