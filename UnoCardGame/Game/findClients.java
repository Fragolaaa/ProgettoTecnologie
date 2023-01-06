package Server;
//questa classe si occupa di cercare i vari giocatori
import java.io.IOException;
import java.net.ServerSocket;

public class findClients extends Thread {
    private Game server = null;
    private ServerSocket serverSocket = null;
    private boolean keepLooking = true;
    
    public FindClients(Game server, ServerSocket serverSocket) {
        this.server = server;
        this.serverSocket = serverSocket;
    }
    
    public void kill() {
        keepLooking = false;
    }
    
    @Override
    public void run() {
        while (keepLooking) {
            try {
                threadHandler newThread = new threadHandler(server, serverSocket.accept());
                newThread.start(); //se trovo un giocatore
            } catch(IOException e) {}
        }
    }
}