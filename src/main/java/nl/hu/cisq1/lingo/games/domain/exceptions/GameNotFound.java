package nl.hu.cisq1.lingo.games.domain.exceptions;


public class GameNotFound extends RuntimeException {

    public GameNotFound(String message){
        super(message);
    }

    public GameNotFound notFound(long gameId) {
        var message = String.format("Het door u opgevraagde spel met %d is niet gevonden", gameId);
        return new GameNotFound(message);
    }
}
