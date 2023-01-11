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
     
    }
     
    public void visit(ChangeColorCardDrawFour changeColorCardDrawFour){

    }

    public void visit(NumberCard numberCard) {
        //nuovo colore/numero da rispettare
    }
    
    public void visit(WildCardDrawTwo wildCardDrawTwo){
        if(game.setCard(client, wildCardDrawTwo)){
            game.nextDraw(2);
        }
        
    }
    
    public void visit(WildCardReverse wildCardReverse){

    }

    public void visit(WildCardSkip wildCardSkip){

    }


}