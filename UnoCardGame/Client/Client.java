package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread{
    
    private int PORT;
    public Socket socket;
    public Client(InetAddress addr, int port) throws IOException
    {
        this.PORT=port;
        socket = new Socket(addr, PORT);

    }


    @Override
    public void run() {
        shared inst = shared.getInstance();

        BufferedReader in;
        try {
            
            inst.setSocket(socket);
            in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true)
            {
                try {
                    String line=in.readLine();
                    System.out.println("Ricevuto dall'altro client: " + line);
                } catch (IOException e) {
                
                }
                    
            }


        } catch (IOException e) {
            inst.toClose=true;  //impossibile prendere lo stream di input
            return;
        }

       
        
    }

}