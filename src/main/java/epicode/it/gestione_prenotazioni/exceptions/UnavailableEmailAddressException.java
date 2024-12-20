package epicode.it.gestione_prenotazioni.exceptions;

public class UnavailableEmailAddressException extends Exception{

    public UnavailableEmailAddressException() {}

    public UnavailableEmailAddressException(String message) {
        super(message);
    }
}
