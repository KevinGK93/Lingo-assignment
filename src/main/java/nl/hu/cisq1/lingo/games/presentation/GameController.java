package nl.hu.cisq1.lingo.games.presentation;

import lombok.RequiredArgsConstructor;
import nl.hu.cisq1.lingo.games.application.GameService;
import nl.hu.cisq1.lingo.games.domain.Game;
import nl.hu.cisq1.lingo.games.domain.Progress;
import nl.hu.cisq1.lingo.games.domain.exceptions.GameNotFound;
import nl.hu.cisq1.lingo.games.presentation.dto.request.RequestAttemptDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @PostMapping
    public Progress startNewLingoGame(){
        try {
            return this.gameService.startNewLingoGame();
        }catch (GameNotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Game getCurrentGameById(@PathVariable Long id){
        try {
            return this.gameService.findGameById(id);
        }catch (GameNotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping("/{id}/attempt")
    public Progress attemptAtLingoWord(@PathVariable Long id, @RequestBody RequestAttemptDto dto) {
        try {
            return this.gameService.lingoWordAttempt(id, dto.getWord());
        } catch (GameNotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping("/{id}/nextRound")
    public Progress nextLingoRound(@PathVariable Long id) {
        try {
            return this.gameService.startNewLingoRound(id);
        } catch (GameNotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }
}
