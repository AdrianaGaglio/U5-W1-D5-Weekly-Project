package epicode.it.gestione_prenotazioni.exceptions;

public class UnavailableUsername extends Exception{

    public UnavailableUsername(){}

    public UnavailableUsername(String message) {
        super(message);
    }
}
