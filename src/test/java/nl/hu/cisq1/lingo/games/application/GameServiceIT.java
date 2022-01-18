package nl.hu.cisq1.lingo.games.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;
import nl.hu.cisq1.lingo.games.domain.exceptions.InvalidAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
public class GameServiceIT {

    @Autowired
    private GameService gameService;

    @Test
    @DisplayName("start a new game")
    void startNewGame(){
        var progress = gameService.startNewLingoGame();

        assertEquals(GameProgress.PLAYING, progress.getGameProgress());
        assertEquals(0, progress.getScore());
    }

    @Test
    @DisplayName("cannot start a new round when still playing")
    void cannotStartNewRound() {
        var currentProgress = gameService.startNewLingoGame();
        Long id = currentProgress.getId();

        assertThrows(
                InvalidAction.class,
                () -> gameService.startNewLingoRound(id)
        );
    }
}
