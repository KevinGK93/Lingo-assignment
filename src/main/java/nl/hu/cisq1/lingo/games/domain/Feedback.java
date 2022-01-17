package nl.hu.cisq1.lingo.games.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.cisq1.lingo.games.domain.enumerations.Mark;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static nl.hu.cisq1.lingo.games.domain.enumerations.Mark.*;

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
        return this.marks.stream().allMatch(Mark.INVALID::equals);
    }

    public List<Mark> checkMarks(String guess, String rightWord) {
        List<Mark> gameMarks = new ArrayList<>();

        // set all indexes abasent based on the rightWord index length\\
        for (int rightWordIndex = 0;
             rightWordIndex < rightWord.length(); rightWordIndex++) {
            gameMarks.add(ABSENT);
        }
        var guessChars = guess.toCharArray();
        var rightWordChars = rightWord.toCharArray();
        int rightWordLength = rightWord.length();

        // compare chars based on index if they are the same set mark CORRECT \\
        IntStream.range(0, rightWordLength).filter
                (wordIndex -> guessChars[wordIndex] == rightWordChars[wordIndex]).forEach(wordIndex -> {
                gameMarks.set(wordIndex, CORRECT);
                rightWordChars[wordIndex] = '.';
        });

        // loop through each guessIndex > based on the rightWordLength.\\
        for (int guessIndex = 0;
             guessIndex < rightWordLength; guessIndex++)
        {
            // loop through each rightWordIndex > based on the rightWordLength.\\
            for (int rightWordIndex = 0;
                 rightWordIndex < rightWordLength; rightWordIndex++)

            { // if the chars of the indexes are equal change absent to present\\
                if (guessChars[guessIndex] == rightWordChars[rightWordIndex]
                        && gameMarks.get(guessIndex) == ABSENT) {
                    gameMarks.set(guessIndex, PRESENT);
                    rightWordChars[rightWordIndex] = '.';
                }
            }
        }
        return gameMarks;
    }

    /**
     * IntelliJ generated
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
