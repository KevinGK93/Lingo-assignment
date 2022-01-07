package nl.hu.cisq1.lingo.games.domain;

import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private Long score = 0L;

    @Enumerated
    private GameProgress gameProgress = GameProgress.WAITING_FOR_ROUND;

    @OneToMany
    @JoinColumn
    @Cascade(CascadeType.ALL)
    private final List<Round> rounds = new ArrayList<>();

    public Game (){}

    public void newRound(String wordToGuess){
        this.rounds.add(new Round(wordToGuess));
        this.gameProgress = gameProgress.ACTIVE;
    }

    private Round getCurrentRound(){
        if(rounds.size() == 0){
            return null;
        }
        else {
            return rounds.get(rounds.size() -1);
        }
    }
    private Round getFinalround(){
        return rounds.get(rounds.size() - 1);
    }



}
