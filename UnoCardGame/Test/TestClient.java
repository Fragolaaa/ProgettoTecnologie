package Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import Game.Cards.Card;
import Network.Message;

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
        Message message = new Message("START", null);
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message res = null;
        try {
            res = (Message) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        if(res != null){
            String[] fields = res.getArg().split(";");
            String cmd = fields[0];
            System.out.println("Comando: " + cmd);
            System.out.println("Messaggio: " + res.getArg());
            ArrayList<Card> hand = new ArrayList<>();
            switch (cmd) {
                case "SETHAND":
                    hand = res.getCards();
                    System.out.println(Card.drawCLI(hand));
                default:
                    //sendMessage("ERROR;Not implemented function");
                    break;
            }
        }
    }

    private boolean join(){
        System.out.println("Inserisci il tuo nome:");
        String name = scanner.nextLine();
        try {
            out.writeObject(new Message("JOIN;"+name, null));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message response = null;
        try {
            response = (Message) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
        String[] fields = response.getArg().split(";");
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

    public TestClient(ObjectOutputStream out, ObjectInputStream in){
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

        TestClient client = new TestClient(objectOutputStream, objectInputStream);
        client.run();

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
