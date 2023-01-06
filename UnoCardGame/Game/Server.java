public class Server{
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
 
