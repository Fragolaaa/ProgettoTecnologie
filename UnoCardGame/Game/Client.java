//gestisce quel che deve fare il client 
package Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import Game.Network.Message;

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
        Message message = new Message(null, null);
        if(!playing){
            message.setArg(game.addPlayer(id, this, name));
            sendMessage(message);
            if(message.getArg().split(";")[0] == "OK") playing = true;
        }else sendMessage(new Message("ERROR;You are already playing", null));
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
            Message message = null;

            try {
                message = (Message) in.readObject();
            } catch (ClassNotFoundException | IOException e) {
                running = false;
            }
            if(message != null){
                String[] fields = message.getArg().split(";");
                String cmd = fields[0];
                System.out.println("Comando: " + cmd);
                System.out.println("Messaggio: " + message.getArg());
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
                    if(message.getCards().size() == 1)
                        game.setCard(this,message.getCards().get(0));
                    else sendMessage(new Message("ERROR;Not implemented function", null));
                    break;
                    default:
                        sendMessage(new Message("ERROR;Not implemented function", null));
                        break;
                }
            }
        }
        if(clientSocket != null){
            reset();
        }
    }

    private void sendMessage(Message message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close(){
        System.out.println("Client: " + clientSocket + " disconnected");
        closeSocket();
    }

    private void reset(){
        System.out.println("Client: " + clientSocket + " disconnected forcefully");
        closeSocket();
    }

    private void closeSocket(){
        if(!clientSocket.isClosed())
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

