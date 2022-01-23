package nl.hu.cisq1.lingo.games.domain;

import lombok.Getter;
import lombok.ToString;
import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;

import java.util.List;

@Getter
@ToString
public class Progress {
    private List<Round> rounds;
    private GameProgress gameProgress;
    private Long id;
    private Long score;

    public Progress(){}

    public Progress(Long id, Long score, List<Round> rounds, GameProgress gameProgress){
        this.id = id;
        this.score = score;
        this.rounds = rounds;
        this.gameProgress = gameProgress;
    }
}
