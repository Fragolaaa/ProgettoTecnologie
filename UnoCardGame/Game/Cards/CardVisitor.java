package Game.Cards;

import Game.Client;
import Game.Game;

public class CardVisitor { //to do: gestire cosa fanno le varie carte
    private final Game game;
    private final Client client;

    public CardVisitor(Game game, Client client) {
        this.game = game;
        this.client = client;
    }

    public void visit(ChangeColorCard ChangeColorCard){
        //il nuovo colore rispettare è....
        if(game.setCard(client, ChangeColorCard)){

        }
     
    }
     
    public void visit(ChangeColorCardDrawFour changeColorCardDrawFour){
        if(game.setCard(client, changeColorCardDrawFour)){
            game.nextDraw(4);
        }
    }

    public void visit(NumberCard numberCard) {
        //nuovo colore/numero da rispettare
        if(game.setCard(client, numberCard)){
    
        }
    }
    
    public void visit(WildCardDrawTwo wildCardDrawTwo){
        if(game.setCard(client, wildCardDrawTwo)){
            game.nextDraw(2);
        }
        
    }
    
    public void visit(WildCardReverse wildCardReverse){
        if(game.setCard(client, wildCardReverse)){
            game.reverse();
        }
    }

    public void visit(WildCardSkip wildCardSkip){
        if(game.setCard(client, wildCardSkip)){
            game.skipPlayer();
        }

    }


}