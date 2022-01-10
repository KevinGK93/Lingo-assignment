package nl.hu.cisq1.lingo.games.application;

import nl.hu.cisq1.lingo.LingoApplication;
import nl.hu.cisq1.lingo.games.data.GameRepository;
import nl.hu.cisq1.lingo.games.domain.Game;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameService gameService;

    @Mock
    private WordService wordService;


    @Test
    @DisplayName("Test round size increased after guess attempt")
    public void checkingDefaultGameStatus(){
        //given
        WordService wordService = mock(WordService.class);
        GameRepository gameRepository = mock(GameRepository.class);
        GameService gameService = new GameService(wordService, gameRepository);

        Game game = new Game();
        game.setId(1L);
        game.newRound("aaien");

        //when
        when(wordService.provideRandomWord(anyInt())).thenReturn("");
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        gameService.startNewLingoRound(1L);

        //then
        assertEquals(game.getRounds().size(), 2);
    }
}
