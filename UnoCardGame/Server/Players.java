//classe che gestisce i vari giocatori connessi al server
//Questo codice crea un server che ascolta le connessioni sulla porta specificata (in questo caso 12345) 
//crea un thread per gestire ogni connessione con un client. Ogni thread riceve i dati dal client, 
//li elabora e invia una risposta al client, quindi chiude il socket.

package server;
import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        // Crea un socket TCP sulla porta specificata
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            // Accetta una connessione in entrata
            Socket clientSocket = serverSocket.accept();

            // Crea un thread per gestire la connessione con il client
            ClientThread clientThread = new ClientThread(clientSocket);
            clientThread.start();
        }
    }
}