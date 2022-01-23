package nl.hu.cisq1.lingo.games.domain;

import lombok.Getter;
import lombok.ToString;
import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;

@Getter
@ToString
public class Progress {
    private GameProgress gameProgress;
    private Long id;
    private Long score;

    public Progress(){}

    public Progress(Long id, Long score, GameProgress gameProgress){
        this.id = id;
        this.score = score;
        this.gameProgress = gameProgress;
    }
}
