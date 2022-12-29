package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class FindClients extends Thread {
    private MyCheckersServer server = null;
    private ServerSocket serverSocket = null;
    private boolean keepLooking = true;
    
    public FindClients(MyCheckersServer server, ServerSocket serverSocket) {
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
                MyCheckersServerThread newThread = new MyCheckersServerThread(server, serverSocket.accept());
                newThread.start();
            } catch(IOException e) {}
        }
    }
}