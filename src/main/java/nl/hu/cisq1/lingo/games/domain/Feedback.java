package nl.hu.cisq1.lingo.games.domain;

import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.games.domain.enumerations.Mark;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
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
}
