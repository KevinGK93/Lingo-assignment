package nl.hu.cisq1.lingo.games.presentation;

import lombok.RequiredArgsConstructor;
import nl.hu.cisq1.lingo.games.application.GameService;
import nl.hu.cisq1.lingo.games.domain.Game;
import nl.hu.cisq1.lingo.games.domain.Progress;
import nl.hu.cisq1.lingo.games.presentation.dto.request.RequestAttemptDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @PostMapping
    public Progress startNewLingoGame(){
        return this.gameService.startNewLingoGame();
    }

    @PostMapping("/{id}")
    public Game getCurrentGameById(@PathVariable Long id){
        return this.gameService.findGameById(id);
    }

    @PostMapping("/{id}/attempt")
    public Progress attemptAtLingoWord(@PathVariable Long id, @RequestBody RequestAttemptDto dto){
        return this.gameService.lingoWordAttempt(id, dto.word);
    }

    @PostMapping("/{id}/nextRound")
    public Progress nextLingoRound(@PathVariable Long id){
        return this.gameService.startNewLingoRound(id);
    }
}
