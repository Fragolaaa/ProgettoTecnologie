//gestisce quel che deve fare il client 
package Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import Game.Cards.Card;
import Game.Cards.colorcards.ColorChangerCardDrawFour;

public class Client extends Thread{

    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Socket clientSocket;
    private final String id;
    private boolean running = true;
    private boolean playing = false;
    private Game game;

    public boolean isPlaying() {
        return playing;
    }

    public Game getGame(){
        return game;
    }

    public void startPlaying(String name) {
        String message = "";
        if(!playing){
            sendMessage(message = game.addPlayer(id, this, name));
            if(message.split(";")[0] == "OK") playing = true;
        }else sendMessage("ERROR;You are already playing");
    }

    public Client(ObjectOutputStream out, ObjectInputStream in, Socket clientSocket, Game game) {
        this.out = out;
        this.in = in;
        this.clientSocket = clientSocket;
        this.game = game;
        id = UUID.randomUUID().toString(); //ID univoco della connessione al client (diverso dal nome)
    }

    @Override
    public void run(){
        while (running) {
            String message = null;

            try {
                message = (String) in.readObject();
            } catch (ClassNotFoundException | IOException e) {
                running = false;
            }
            if(message != null){
                String[] fields = message.split(";");
                String cmd = fields[0];
                System.out.println("Comando: " + cmd);
                System.out.println("Messaggio: " + message);
                switch (cmd) {
                    case "RST":
                        close();
                        running = false;
                        break;
                    case "JOIN":
                        startPlaying(fields[1]);
                        break;
                    case "START":
                        game.start();
                    case "SETCARD":
                        game.setCard(this,fields[1]);
                    break;
                    default:
                        sendMessage("ERROR;Not implemented function");
                        break;
                }
            }
        }
        if(clientSocket != null){
            reset();
        }
    }

    private void sendMessage(String message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close(){
        System.out.println("Client: " + clientSocket + " disconnected");
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reset(){
        System.out.println("Client: " + clientSocket + " disconnected forcefully");
        sendMessage("RST;Server Error");
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToClient(Object message){
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
