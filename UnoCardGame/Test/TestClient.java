package Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import UnoCardGame.Server.cards.Card;
import UnoCardGame.Server.cards.wildcards.WildCardDrawTwo;

public class TestClient {
    private static final Scanner scanner = new Scanner(System.in);
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private boolean playing = false;
 
    private void run(){
        if(join()){
            play();
            //quit();
        }
        else{
            System.out.println("Login failed");
        }
    }

    private void play(){
        //while(true);
        try {
            out.writeObject("START");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object res = null;
        try {
            res = in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        ArrayList<Card> hand = (ArrayList<Card>) res;
        System.out.println(Card.drawCLI(hand));
    }

    private boolean join(){
        System.out.println("Inserisci il tuo nome:");
        String name = scanner.nextLine();
        try {
            out.writeObject("JOIN;"+name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String response = null;
        try {
            response = (String) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
        String[] fields = response.split(";");
        String cmd = fields[0];
        switch (cmd) {
            case "OK":
                System.out.println(fields[1]);
                return true;
            case "ERROR":
                System.out.println(fields[1]);
                break;
            case "RST":
                System.out.println("You got disconnected forcefully");
                break;
            default:
                System.out.println("Something went wrong"); //comando non riconosciuto da parte server (molto improbabile)
                break;
        }
        return false;
    }

    public Client(ObjectOutputStream out, ObjectInputStream in){
        this.out = out;
        this.in = in;
        playing = true;
    }
    public static void main(String[] args) {

        System.out.println("Insert the server IP");
        InetAddress IP = null;
        Socket socket = null;
        try {
            String ip = scanner.nextLine();
            IP = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            System.out.println("The submitted IP is wrong");
            return;
        }
        System.out.println("Insert the server PORT");
        try {
            String port = scanner.nextLine();
            socket = new Socket(IP, Integer.parseInt(port));
        } catch (IOException e) {
            System.out.println("ConnectionRefused");
            return;
        }

        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Client client = new Client(objectOutputStream, objectInputStream);
        client.run();

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
