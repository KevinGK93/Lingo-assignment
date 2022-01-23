package nl.hu.cisq1.lingo.games.domain.exceptions;

public class ExceptionMessages extends RuntimeException {

    private static final long serialVersionUID = 1348771109171435607L;

    public ExceptionMessages(String message){
        super(message);
    }
}