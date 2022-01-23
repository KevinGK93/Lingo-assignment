package nl.hu.cisq1.lingo.games.domain;

import nl.hu.cisq1.lingo.games.domain.enumerations.ErrorMessages;
import nl.hu.cisq1.lingo.games.domain.exceptions.ExceptionMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @ParameterizedTest
    @DisplayName("Test the score calculation based on the amount of attempts")
    @MethodSource("scoreStream")
    void calculateGameScore(int score, int wordLength){
        //Given
        var round = new Round("boeken");
        IntStream.range(0, wordLength).mapToObj(index -> "boeken").forEach(round::attemptAtWord);

        //Then
        assertEquals(score, round.determineScore());
    }

    //When
    static Stream<Arguments> scoreStream(){
        return Stream.of(
                Arguments.of(45, 1),
                Arguments.of(40, 2),
                Arguments.of(35, 3),
                Arguments.of(30, 4),
                Arguments.of(25, 5)
        );
    }
    @Test
    @DisplayName("Test guessLength is not equal to the correct wordLength")
    void guessLength(){
        //Given
        var round = new Round("hodor");

        //Then
        assertThrows(ExceptionMessages.class,
                ()-> {
            round.attemptAtWord("boer");
            }
        );
    }
    @Test
    @DisplayName("The correct word is guessed")
    void wordIsGuessedTest(){
        var round = new Round("kevin");
        round.attemptAtWord("kevin");
        assertTrue(round.isWordGuessed());
    }




    }

