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
    @GeneratedValue
    private Long id;

    private Long score = 0L;

    @Enumerated
    private GameProgress gameProgress = GameProgress.WAITING_FOR_ROUND;

    @OneToMany
    @JoinColumn
    @Cascade(CascadeType.ALL)
    private final List<Round> rounds = new ArrayList<>();

    public Game() {
        //empty constructor required by Hibernate.
    }

    public void newRound(String wordToGuess) {
        this.rounds.add(new Round(wordToGuess));
        this.gameProgress = GameProgress.ACTIVE;
    }

    private Round getCurrentRound() {
        if (rounds.isEmpty()) {
            return null; //TODO: make better exception return.
        } else {
            return rounds.get(rounds.size() - 1);
        }
    }

    private Round getPreviousRound() {
        return rounds.get(rounds.size() - 1);
    }

    // make sure that wordLength can not go higher then 7 or lower then 5 \\
    public int getNextWordToGuessLength() {
        var wordLength = this.getPreviousRound().getWordToGuessLength() + 1;

        if (wordLength == 8 || wordLength < 5) {
            wordLength = 5;
        }
        return wordLength;
    }

    //TODO: clean up code
    public void gameAttempt(String guess) {
        var currentRound = this.getCurrentRound();

        if (currentRound != null) {
            currentRound.attemptAtWord(guess);
        }

        if (currentRound != null && currentRound.thePlayerHasLost()) {
            this.gameProgress = GameProgress.LOST;
        }

        if (currentRound != null && currentRound.isWordGuessed()) {
            this.score += currentRound.determineScore();
            this.gameProgress = GameProgress.WON;
        }
    }

    //TODO: possible gameProgress status check required to verify progress
    public Progress gameProgress() {
        return new Progress(rounds, gameProgress, id, score);
    }
}
