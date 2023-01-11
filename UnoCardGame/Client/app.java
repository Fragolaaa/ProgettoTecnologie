package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class app {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        Client s = new Client(InetAddress.getByName("localhost"), 8080);
        s.start();

        Keyboarlistener kl = new Keyboarlistener();
        kl.start();


        s.join();
        
    }
}
