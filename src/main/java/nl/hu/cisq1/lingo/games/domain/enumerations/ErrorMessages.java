package nl.hu.cisq1.lingo.games.domain.enumerations;

public enum ErrorMessages {
    WordLengthNotEqualToGuessLength("Het door u opgegeven woord heeft teveel letters"),
    GameIdNotFound("Het door u opgegeven id komt niet overeen met een id in het systeem");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
