package nl.hu.cisq1.lingo.games.domain;

import nl.hu.cisq1.lingo.games.domain.enumerations.Mark;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.games.domain.enumerations.Mark.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeedbackTest {

    @Test
    @DisplayName("All letters in the guessed word are correct")
    void testWordIsCorrect(){
        var feedback = new Feedback("appel", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));
        assertTrue(feedback.wordIsGuessed());
    }

    @Test
    @DisplayName("Word is invalid if all letters are invalid")
    void testWordIsInvalid(){
        var feedback = new Feedback("appel", List.of(INVALID, INVALID, INVALID, INVALID, INVALID));
        assertTrue(feedback.wordIsInvalid());
    }

    @ParameterizedTest
    @DisplayName("test marks with each attempt")
    @MethodSource("feedbackStream")
    void testFeedbackSteamMarks(String guess, List<Mark> givenMarks, String guessedWord){
        var feedback = new Feedback();
        var marks = feedback.checkMarks(guess, guessedWord);
        assertEquals(givenMarks, marks);
    }

    static Stream<Arguments> feedbackStream(){
        return Stream.of(
                Arguments.of("appel", List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT), "azikn"),
                Arguments.of("appel", List.of(CORRECT, CORRECT, ABSENT, ABSENT, ABSENT), "apian"),
                Arguments.of("appel", List.of(PRESENT, PRESENT, CORRECT, PRESENT, PRESENT), "leppa"),
                Arguments.of("appel", List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT), "appen"),
                Arguments.of("appel", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT), "appel")
        );
    }
}
