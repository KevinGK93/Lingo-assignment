package nl.hu.cisq1.lingo.games.application;

import nl.hu.cisq1.lingo.games.data.GameRepository;
import nl.hu.cisq1.lingo.games.domain.Game;
import nl.hu.cisq1.lingo.games.domain.Progress;
import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;
import nl.hu.cisq1.lingo.games.domain.exceptions.ExceptionMessages;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {


    @Test
    @DisplayName("round size increases when a new round is started")
    void testRoundSize(){
        //given
        var wordService = mock(WordService.class);
        var gameRepository = mock(GameRepository.class);
        var gameService = new GameService(wordService, gameRepository);
        Long id = 1L;

        Game game = new Game();
        game.setId(id);
        game.newRound("adios");

        //when
        when(wordService.provideRandomWord(anyInt())).thenReturn("kevin");
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        gameService.startNewLingoRound(id);

        //then
        assertEquals(2, game.getRounds().size());
    }

    @Test
    @DisplayName("When a new round starts the status is playing")
    void roundStatusTest() {
        //Given
        WordService wordService = mock(WordService.class);
        GameRepository repository = mock(GameRepository.class);
        GameService gameService = new GameService(wordService, repository);

        //When
        when(wordService.provideRandomWord(anyInt())).thenReturn("blank");
        var progress = gameService.startNewLingoGame();

        //then
        assertEquals(GameProgress.PLAYING, progress.getGameProgress());
    }

    @Test
    @DisplayName("Game exception if game is not found")
    void gameNotFoundExceptionTest(){
        WordService wordService = mock(WordService.class);
        GameRepository repository = mock(GameRepository.class);
        GameService gameService = new GameService(wordService, repository);

        assertThrows(ExceptionMessages.class, ()
                -> gameService.startNewLingoRound(0L));
    }

    @ParameterizedTest
    @DisplayName("When a player has guessed the feedbackhistory should increase")
    @MethodSource("feedbackHistoryArguments")
    void testFeedbackHistory(int guessAttempts, GameProgress gameProgress){
        //Given
        WordService wordService = mock(WordService.class);
        GameRepository repository = mock(GameRepository.class);
        GameService gameService = new GameService(wordService, repository);

        var game = new Game();
        var progress = new Progress();
        when(repository.findById(anyLong())).thenReturn(Optional.of(game));
        game.newRound("advies");

        for (int index = 0;
        index < guessAttempts; index++) {
            progress = gameService.lingoWordAttempt(1L, "slopen");
        }

        //Then
        assertEquals(progress.getGameProgress(), gameProgress);
    }

    static Stream<Arguments> feedbackHistoryArguments(){
        return Stream.of(
                Arguments.of(1, 1, GameProgress.PLAYING),
                Arguments.of(2, 2, GameProgress.PLAYING),
                Arguments.of(5, 5, GameProgress.LOST)
        );
    }


}
