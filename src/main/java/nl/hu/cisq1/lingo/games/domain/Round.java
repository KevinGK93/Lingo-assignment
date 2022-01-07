package nl.hu.cisq1.lingo.games.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "round")
public class Round {
    public static final int MAX_ATTEMPTS = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String wordToGuess;

    @OneToMany
    @JoinColumn
    @Cascade(CascadeType.ALL)
    private final List<Feedback> feedbackHistory = new ArrayList<>();

    public Round(){}

    public Round(String wordToGuess){
        this.wordToGuess = wordToGuess;
    }

}

