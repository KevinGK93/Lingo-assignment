package nl.hu.cisq1.lingo.games.domain;

import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @ParameterizedTest
    @DisplayName("Testing the different game progress states")
    @MethodSource("gameProgressStates")
    void progressStatesTest(int guessAttempts, String guess, GameProgress result) {
        //Given
        var game = new Game();
        game.newRound("adonis");

        IntStream.range(0, guessAttempts).mapToObj(index -> guess).forEach(game::gameAttempt);

        //Then
        assertEquals(result, game.getGameProgress());

        }
    //When
    static Stream<Arguments> gameProgressStates() {
        return Stream.of(
                Arguments.of(1, "advies", GameProgress.PLAYING),
                Arguments.of(3, "adonis", GameProgress.WON),
                Arguments.of(2, "advent", GameProgress.PLAYING),
                Arguments.of(5, "afbouw", GameProgress.LOST)
        );
    }

    @ParameterizedTest
    @DisplayName("Set next wordLength when word is guessed")
    @MethodSource("wordLengthDataStream")
    void nextWordtLengthTest(String guess, int result){
        //Given
        var game = new Game();
        game.newRound(guess);

        //Then
        assertEquals(result, game.getNextWordToGuessLength());
    }
    //When
    static Stream<Arguments> wordLengthDataStream(){
        return Stream.of(
                Arguments.of("woord", 6),
                Arguments.of("honden", 7),
                Arguments.of("gras", 5),
                Arguments.of("buit", 5)
        );
    }

    @Test
    @DisplayName("the starting game status is waiting_for_round")
    void startingGameStatus(){
        var game = new Game();
        assertEquals(GameProgress.WAITING_FOR_ROUND,game.getGameProgress());
    }



}
