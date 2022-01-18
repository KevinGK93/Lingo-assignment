package nl.hu.cisq1.lingo.games.application;

import nl.hu.cisq1.lingo.games.data.GameRepository;
import nl.hu.cisq1.lingo.games.domain.Game;
import nl.hu.cisq1.lingo.games.domain.Progress;
import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {


    @Test
    @DisplayName("asd")
    void testRoundSizeAfterAttempt(){
        //given
        var wordService = mock(WordService.class);
        var gameRepository = mock(GameRepository.class);

        var gameService = new GameService(wordService, gameRepository);

        Game game = new Game();
        game.setId(1L);
        game.newRound("appel");

        //when
        when(wordService.provideRandomWord(anyInt())).thenReturn("");
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        gameService.startNewLingoRound(1L);

        //then
        assertEquals(2, game.getRounds().size());
    }

    @Test
    @DisplayName("new round always starts with playing")
    void test_createLingoRound() {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(6))
                .thenReturn("maaien");

        //given
        Game game = new Game();
        game.newRound("baard");
        game.gameAttempt("baard");

        //when
        GameRepository repository = mock(GameRepository.class);
        when(repository.findById(anyLong())).thenReturn(Optional.of(game));

        GameService gameService = new GameService(wordService, repository);
        Progress progress = gameService.startNewLingoRound(1L);

        //then
        assertEquals(GameProgress.PLAYING, progress.getGameProgress());

        // The Progress gameProgress in Game.class gives a NullPointerException on id without
        // the weird if statement. I cant figure out for the life of me why.
    }

//    static Stream<Arguments> gameProgressAttemptArguments() {
//        return Stream.of(
//                Arguments.of(1, 1, GameProgress.WAITING_FOR_ROUND),
//                Arguments.of(2, 2, GameProgress.PLAYING),
//                Arguments.of(3, 3, GameProgress.PLAYING),
//                Arguments.of(4, 4, GameProgress.PLAYING),
//                Arguments.of(5, 5, GameProgress.PLAYING)
//
//        );
//    }
}
