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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String attempt;

    @ElementCollection
    private List<Mark> marks;

    public Feedback(){}

    public Feedback(String attempt, List<Mark> marks){
        this.attempt = attempt;
        this.marks = marks;
    }

    public boolean wordIsGuessed(){
        return this.marks.stream().allMatch(Mark.CORRECT::equals);
    }

    public boolean wordIsInvalid(){
        return this.marks.stream().allMatch(Mark.ABSENT::equals);
    }

    public List<Mark> checkMarks(String guess, String rightWord){
        List<Mark> gameMarks = new ArrayList<>();

        for (int rightWordIndex = 0;
             rightWordIndex < rightWord.length(); rightWordIndex++){
            gameMarks.add(Mark.ABSENT);
        }
        var guessChars = guess.toCharArray();
        var rightWordChars = rightWord.toCharArray();
        int rightWordLength = rightWord.length();

        for (int i = 0;
            i < rightWordLength; i++){
            if (guessChars[i] == rightWordChars[i]){
                gameMarks.set(i, Mark.CORRECT);
                rightWordChars[i] = '-';
            }
        }
        for (int i = 0;
            i < rightWordLength; i++){
            for (int j = 0;
                j < rightWordLength; j++){
                if (guessChars[i] == rightWordChars[j] && gameMarks.get(i) == Mark.ABSENT){
                    gameMarks.set(i, Mark.PRESENT);
                    rightWordChars[j] = '-';
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
