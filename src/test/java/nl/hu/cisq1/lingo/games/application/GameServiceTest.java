package nl.hu.cisq1.lingo.games.application;

import nl.hu.cisq1.lingo.games.data.GameRepository;
import nl.hu.cisq1.lingo.games.domain.Game;
import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Test
    @DisplayName("Test round size increased after guess attempt")
    void testRoundSizeAfterAttempt(){
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
        assertEquals(2, game.getRounds().size());
    }

    static Stream<Arguments> gameProgressAttemptArguments() {
        return Stream.of(
                Arguments.of(1, 1, GameProgress.ACTIVE),
                Arguments.of(2, 2, GameProgress.ACTIVE),
                Arguments.of(3, 3, GameProgress.ACTIVE),
                Arguments.of(4, 4, GameProgress.ACTIVE),
                Arguments.of(5, 5, GameProgress.LOST)
        );
    }
}
