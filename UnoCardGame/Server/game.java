package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class game extends Thread {
    private ServerSocket serverSocket = null;
    private FindClients clientListener = null;
    private HashMap clients = null;
    private HashMap games = null;
    private int num_connected = 0;
    private int max_connections;
    private boolean listening = false;
    private String direction="forwards";
    private String currentColor="";
    
    public int addClient(threadHandler client) {
        if (clients.containsKey(client.name)) {
            System.err.println("Name already taken");
            return 2;
        } else if (num_connected >= max_connections) {
            System.err.println("Too many connections!");
            return 1;
        } else {
            clients.put(client.name, client);
            num_connected += 1;
            sendClientList();//invio la lista dei giocatori connessi agli altri giocatori
            return 0;
        }
    }
    
    public void removeClient(threadHandler client) {
        if (clients.containsKey(client.name)) {
            clients.remove(client.name); //rimuovo il giocatore dalla lista
            num_connected -= 1;
            sendClientList();//invio la lista dei giocatori connessi agli altri giocatori
        } else {
            System.err.println("Player does not exist");
        }
    }
    
    private void sendClientList() {
        String[] clientIPList = new String[clients.size()];
        threadHandler[] clientsList = (threadHandler[]) clients.values().toArray(new threadHandler[0]);
        String names = "###name_list=";
        
        for(int n = 0; n < clientsList.length; n++) {
            clientIPList[n] = String.valueOf(clientsList[n].getSocket().getRemoteSocketAddress()) + " " + clientsList[n].name;
            names += clientsList[n].name + ",";
        }
        names = names.substring(0, names.length()-1) + "###";
        gui.writeClientList(clientIPList);
        
        for(int n = 0; n < clientsList.length; n++) {
            if (clientsList[n] != null) {
                clientsList[n].sendMessage("server", names);
            }
        }
    }
    
    public int startListener(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clients = new HashMap();
            games = new HashMap();
            clientListener = new FindClients(this, serverSocket);
            clientListener.start();
            listening = true;
            return 1;
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            return 0;
        } catch (IllegalArgumentException e) {
            System.err.println("Bad port");
            return 0;
        }
    }
    
    public int stopListening() {
        try {
            if (listening) {
                for (threadHandler client : (threadHandler[]) clients.values().toArray(new threadHandler[0])) {
                    client.sendMessage("server", "###disconnected###");
                    client.kill();
                }
                clientListener.kill();
                clientListener = null;
                serverSocket.close();
                gui.writeClientList(new String[0]);
                clients.clear();
                games.clear();
                num_connected = 0;
                listening = false;
            }
            return 1;
        } catch (IOException e) {
            System.err.println("Could not close server.");
            return 0;
        }
    }
    
    public void sendMessage(String reciever, String sender, String message) {
        if (clients.containsKey(reciever)) {
            threadHandler threadReciever = (threadHandler) clients.get(reciever);
            threadReciever.sendMessage(sender, message);
        } else if (reciever.equals("all")) {
            threadHandler[] clientsList = (threadHandler[]) clients.values().toArray(new threadHandler[0]);
            for (threadHandler c : clientsList) {
                c.sendMessage(sender, message);
            }
        } else {
            System.err.println("Error sending message to " + reciever);
        }
    }
    
    public void newGame(String client1, String client2) { //da modoficare per più host
        
    }
    
    public void endGame(String loser, String winner) {
        String gameString = "";
        if (games.containsKey(loser+":"+winner)) {
            gameString = loser+":"+winner;
        } else if (games.containsKey(winner+":"+loser)) {
            gameString = winner+":"+loser;
        } else {
            System.err.println("game you are trying to end does not exist");
        }
        
        sendMessage(winner, loser, "###you_won###");
        games.remove(gameString);
    }
    
    public void restartGame(String client1, String client2) {
        table game = new table(client1, client2);
        games.put(client1+":"+client2, game);
        sendMessage(client1, client2,
                "###new_game_restarted###board="+game.getBoard()+"###turn="+game.getTurn()+"###");
        sendMessage(client2, client1,
                "###new_game_restarted###board="+game.getRotatedBoard(game.getBoard())+"###turn="+game.getTurn()+"###");
    }
    
    public void reverse(String direction){
        if(direction.equals("forwards"))
            direction="backwards";
        else if(direction.equals("backwards"))
            direction="forwards";
    }

    public void changeColor(String color){
        currentColor=color;
    }

    public void skip(String player){
            //prendo il giocatore che ha messo lo skip e skippo il prossimo nella lista 
        
    }

    public void drawCards(String player){
        String mess="###Draw_";
        String rec;
        // Print keys
        for (String i : clients.keySet()) {
            if(i.equals(player)){
                rec=clients[i+1];
            }
        }
        //dico al giocatore che deve prendere n carte, funziona in caso di +2, +4 o pescata random per mancanza colore/numero
        sendMessage(player, mess);
    }


} 