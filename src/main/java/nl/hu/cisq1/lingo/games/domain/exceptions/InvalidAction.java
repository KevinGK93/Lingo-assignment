package nl.hu.cisq1.lingo.games.domain.exceptions;

import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;

public class InvalidAction extends RuntimeException {
    public InvalidAction(String message) {
        super(message);
    }

    public static InvalidAction invalidAction() {
        var message = "Het door u gestuurde verzoek kon niet worden verwerkt";
        return new InvalidAction(message);
    }

    public static InvalidAction cannotStartNewRound(GameProgress status) {
        var message = String.format("kan het spel niet starten met: %s status", status);
        return new InvalidAction(message);
    }
}
