package Game.Cards;

import Game.Game;

public class CardVisitor { //to do: gestire cosa fanno le varie carte
    private final Game game;

    public CardVisitor(Game game) {
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