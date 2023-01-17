package Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import uno.server.cards.NumberCard;
// import uno.server.cards.Card;
// import uno.server.cards.colorcards.ColorChangerCard;
// import uno.server.cards.colorcards.ColorChangerCardDrawFour;
// import uno.server.cards.wildcards.WildCardDrawTwo;
// import uno.server.cards.wildcards.WildCardReverse;
// import uno.server.cards.wildcards.WildCardSkipTurn;

public class Server{
    public static void main(String[] args){
        int PORT = 12345;
    
        Server server = new Server(PORT);
    
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            System.err.println("Connection Failed");
        }
        //Deck deck = new Deck();
        // ColorChangerCardDrawFour card = new ColorChangerCardDrawFour();
        // System.out.println(card.drawCLI());
        // card.use(1);
        // System.out.println(card.drawCLI());
        // System.out.print(new WildCardDrawTwo(0).drawCLI());
        // System.out.print(new WildCardReverse(2).drawCLI());
        // System.out.print(new WildCardSkipTurn(3).drawCLI());
        // System.out.print(new ColorChangerCard().drawCLI());
        // System.out.print(new NumberCard(0, 10).drawCLI());
        // ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(
        //     new WildCardDrawTwo(0),
        //     new WildCardReverse(1),
        //     new WildCardSkipTurn(2),
        //     new NumberCard(3, 10),
        //     new ColorChangerCard(),
        //     new ColorChangerCardDrawFour(),
        //     card
        // ));
        // System.out.println(Card.drawCLI(cards));
        server.run(serverSocket);
    }

    //private final int PORT;
    private static ArrayList<Client> clients = new ArrayList<>();

    public Server(int PORT){
        //this.PORT = PORT;
    }

    public void run(ServerSocket serverSocket){
        try {
            Game game = new Game();
            while(true){
                Socket clientSocket;
                
                clientSocket = serverSocket.accept();
                System.out.println("Client " + clientSocket + " is connected");

                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                Client client = new Client(out, in, clientSocket, game);

                clients.add(client);

                client.start();
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                if(serverSocket != null){
                    serverSocket.close();
                    //blocca tutti i client
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}