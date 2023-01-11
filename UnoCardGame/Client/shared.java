package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class shared {
    
    private static shared _instance = null;
    public static shared getInstance(){
        if(_instance==null)
            _instance=new shared();

        return _instance;
    }
    private shared(){}

    private Socket socket;
    private PrintWriter out;
    public boolean toClose=false;


    public void setSocket(Socket socket) throws IOException
    {
        this.socket=socket;
         out =new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
    }


    
    public Socket getSocket() {
        return socket;
    }
    public PrintWriter getOut() {
        return out;
    }
    
    
}
