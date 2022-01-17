package nl.hu.cisq1.lingo.games.domain.exceptions;

public class InvalidAction extends RuntimeException {
    public InvalidAction(String message){
        super(message);
    }

    public InvalidAction invalidAction(){
        var message = "Het door u gestuurde verzoek kon niet worden verwerkt";
        return new InvalidAction(message);
    }
}
