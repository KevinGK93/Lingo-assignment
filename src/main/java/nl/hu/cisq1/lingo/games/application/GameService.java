package nl.hu.cisq1.lingo.games.application;

import lombok.RequiredArgsConstructor;
import nl.hu.cisq1.lingo.games.data.GameRepository;
import nl.hu.cisq1.lingo.games.domain.Game;
import nl.hu.cisq1.lingo.games.domain.Progress;
import nl.hu.cisq1.lingo.games.domain.enumerations.ErrorMessages;
import nl.hu.cisq1.lingo.games.domain.exceptions.ExceptionMessages;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    private final WordService wordService;
    private final GameRepository gameRepository;

    public Game findGameById(Long gameId){
        return this.gameRepository.findById(gameId)
                .orElseThrow(()-> new ExceptionMessages(ErrorMessages.GAME_ID_NOT_FOUND.getErrorMessage()));
    }

    public Progress startNewLingoRound(Long gameId){
        var lingoGame = findGameById(gameId);

        var newGameLength = lingoGame.getNextWordToGuessLength();
        var wordToGuess = this.wordService.provideRandomWord(newGameLength);
        lingoGame.newRound(wordToGuess);
        gameRepository.save(lingoGame);

        return lingoGame.gameProgress();
    }

    public Progress startNewLingoGame(){
        var lingoGame = this.wordService.provideRandomWord(5);

        var newGame = new Game();
        newGame.newRound(lingoGame);
        gameRepository.save(newGame);

        return newGame.gameProgress();
    }

    public Progress lingoWordAttempt(Long gameId, String attemptedWord){
        var lingoGame = findGameById(gameId);
        lingoGame.gameAttempt(attemptedWord);
        gameRepository.save(lingoGame);

        return lingoGame.gameProgress();
    }
}
