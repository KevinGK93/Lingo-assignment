package nl.hu.cisq1.lingo.games.domain.exceptions;


public class GameNotFound extends RuntimeException {

    public GameNotFound(String message){
        super(message);
    }

    public GameNotFound GameNotFound(){
        var message = "Het door u opgevraagde spel kan niet worden gevonden.";
        return new GameNotFound(message);
    }
}
