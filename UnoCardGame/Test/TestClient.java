package Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import Game.Actions.ActionSetCard;

public class TestClient {

    public static void main(String[] args) {
        Socket socket = null;

        try {
            socket = new Socket(InetAddress.getByName("localHost"), 12345);
        } catch (IOException e) {
            System.out.println("ConnectionRefused");
        }

        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        while(true){
            
            Scanner s = new Scanner(System.in);
            s.nextInt();

            ActionSetCard actionSetCard = new ActionSetCard();
            try {
                objectOutputStream.writeObject(actionSetCard);
            } catch (IOException e) {
                e.printStackTrace();
        }
        
            Object genericMessage2 = null;
            try {
                genericMessage2 = (Object) objectInputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            System.out.println(genericMessage2);
        }
    }
}
