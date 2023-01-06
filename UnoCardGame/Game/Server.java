package Game;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Game.notifies.NotifyCardChanged;
import Game.notifies.NotifyPlayerHandChanged;

public class Server {

    public final int PORT;

    public ArrayList<Client> clients = new ArrayList<>();

    public Server(int PORT){
        this.PORT = PORT;
    }

    public void run(ServerSocket serverSocket){
        try {
            while(true){
                Socket socketClient;
                    
                socketClient = serverSocket.accept();
                System.out.println(socketClient + " joined the lobby");
    
                ObjectOutputStream out = new ObjectOutputStream(socketClient.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socketClient.getInputStream());
    
                Client client = new Client(PORT, out, in, socketClient);
    
                clients.add(client);
    
                //client.start();

                if(clients.size() == 4){ //to do: chiedi al client quanti giocatori vuole
                    startGame();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        Game game = new Game((ArrayList) clients.subList(0, 3));
        game.start();
    }

    public static void main(String[] args) {
        int port = 12345;
        Server server = new Server(port);

        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println("Connection Failed");
        }

        server.run(serverSocket);
    }
}
