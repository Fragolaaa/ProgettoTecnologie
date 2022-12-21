//classe giocatore

//to do: l'host deve ricevere i messaggi dal server, quando è il suo turno e poi comunicare al server la sua mossa
//eventuali "regole infrante" come colore sbagliato non devono essere ammesse direttamente a livello del client


public class Player(){
    String nickname;
    CardDeck hand = new CardDeck(); //l'utente ha un deck di carte da cui scegliere
    
    Socket client = new( //socket per inviare poi i messaggi al server
    ipEndPoint.AddressFamily, 
    SocketType.Stream, 
    ProtocolType.Tcp);

    await client.ConnectAsync(ipEndPoint);

    public Player(String nickname, CardDeck hand){ //costruttore del giocatore
        this.nickname=nickname;
        this.hand=hand;
    }
    public String getNickname(){
        return nickname;
    }

    public sendMessage(){
        //Invio messaggio al server
    var message = ""; //invio al server il nickname, la carta che ho deciso di buttare + eventuale bottone uno
    var messageBytes = Encoding.UTF8.GetBytes(message);
    _ = await client.SendAsync(messageBytes, SocketFlags.None);
    Console.WriteLine($"*nickname*: \"{message}\"");
    }

    public receiveMessage(){
            
    // ack
    var buffer = new byte[1_024];
    var received = await client.ReceiveAsync(buffer, SocketFlags.None);
    var response = Encoding.UTF8.GetString(buffer, 0, received);
    if (response == "<|ACK|>")
    {
        
        break;
    }
    }

}