package nl.hu.cisq1.lingo.games.presentation;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.games.application.GameService;
import nl.hu.cisq1.lingo.games.domain.Progress;
import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
public class GameControllerIT {

    @Autowired
    private GameService gameService;

    @Test
    @DisplayName("hodor")
    void startNewGame() {
        Progress progress = gameService.startNewLingoGame();

        assertEquals(GameProgress.PLAYING, progress.getGameProgress());
        assertEquals(0, progress.getScore());
    }


//    @Test
//    @DisplayName("hodor2")
//    void cannotStartNewRound(){
//        Progress currentProgress = gameService.startNewLingoGame();
//        Long id = currentProgress.getId();
//
//        assertThrows(
//                null,
//                () -> gameService.startNewLingoRound(id));
//    }
}
