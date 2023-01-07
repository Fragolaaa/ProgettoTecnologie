package Game.Cards;

import Game.Client;
import Game.Game;

public class CardVisitor {
    private final Client client;
    private final Game game;

    public CardVisitor(Client client, Game game) {
        this.client = client;
        this.game = game;
    }

    public void visit(ChangeColorCard ChangeColorCard){
        //il nuovo colore rispettare è....
     
    }
     
    public void visit(ChangeColorCardDrawFour  changeColorCardDrawFour){

    }

    public void visit(NumberCard numberCard) {
        //nuovo colore/numero da rispettare
    }
    
    public void visit(WildCardDrawTwo wildCardDrawTwo){

    }
    
    public void visit(WildCardReverse wildCardReverse){

    }

    public void visit(WildCardSkip wildCardSkip){
        
    }


}