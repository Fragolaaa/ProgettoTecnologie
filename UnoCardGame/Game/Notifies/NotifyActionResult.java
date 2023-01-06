package Game.Notifies;

public class NotifyActionResult extends Notify{
    public int status; //status code
    //public boolean actionResult; //true o false
    public String message = ""; //messaggio di errore nel caso in cui sia andata male
}

//il client fa:
/*
 * if(NotifyActionResult.status == <statusCode di errore>)
 *  mostra al player NotifyActionResult.message();
 */