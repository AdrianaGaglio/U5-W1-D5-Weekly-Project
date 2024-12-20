package epicode.it.gestione_prenotazioni.exceptions;

public class PastDateException extends Exception {

    public PastDateException() {}

    public PastDateException(String message) {
        super(message);
    }
}
