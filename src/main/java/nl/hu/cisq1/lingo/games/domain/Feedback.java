package nl.hu.cisq1.lingo.games.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.cisq1.lingo.games.domain.enumerations.Mark;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue
    private Long id;
    private String attempt;

    @ElementCollection
    private List<Mark> marks;

    public Feedback() {
    }

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
    }

    public boolean wordIsGuessed() {
        return this.marks.stream().allMatch(Mark.CORRECT::equals);
    }

    public boolean wordIsInvalid() {
        return this.marks.stream().allMatch(Mark.ABSENT::equals);
    }

    public List<Mark> checkMarks(String guess, String rightWord) {
        List<Mark> gameMarks = new ArrayList<>();

        // set every mark in the word absent based on the length of the rightWord
        for (int rightWordIndex = 0;
             rightWordIndex < rightWord.length(); rightWordIndex++) {
            gameMarks.add(Mark.ABSENT);
        }
        var guessChars = guess.toCharArray();
        var rightWordChars = rightWord.toCharArray();
        int rightWordLength = rightWord.length();

        // for loop through each index
        for (int wordIndex = 0;
             wordIndex < rightWordLength; wordIndex++) {

            // if the index of the characters matches: set Mark.Correct
            if (guessChars[wordIndex] == rightWordChars[wordIndex]) {
                gameMarks.set(wordIndex, Mark.CORRECT);
                rightWordChars[wordIndex] = '.';
            }
        }
        // loop through each guessIndex > based on the rightWordLength
        for (int guessIndex = 0;
             guessIndex < rightWordLength; guessIndex++)
        {
            // loop through each rightWordIndex > based on the rightWordLength
            for (int rightWordIndex = 0;
                 rightWordIndex < rightWordLength; rightWordIndex++)

            { // if the char of guessIndex == the char of rightWordIndex and gameMarks index = absent set Mark.Present
                if (guessChars[guessIndex] == rightWordChars[rightWordIndex]
                        && gameMarks.get(guessIndex) == Mark.ABSENT) {
                    gameMarks.set(guessIndex, Mark.PRESENT);
                    rightWordChars[rightWordIndex] = '.';
                }
            }
        }
        return gameMarks;
    }

    /**
     * InteliJ generated
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return marks.equals(feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marks);
    }
}
