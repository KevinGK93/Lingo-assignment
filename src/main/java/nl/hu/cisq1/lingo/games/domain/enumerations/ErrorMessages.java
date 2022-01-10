package nl.hu.cisq1.lingo.games.domain.enumerations;

public enum ErrorMessages {
    Word_Length_Not_Equal_To_Guess_length("Het door u opgegeven woord heeft teveel letters"),
    Game_Id_Not_Found("Het door u opgegeven id komt niet overeen met een id in het systeem");

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
