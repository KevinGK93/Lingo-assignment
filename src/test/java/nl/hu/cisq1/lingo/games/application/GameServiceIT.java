package nl.hu.cisq1.lingo.games.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.games.data.GameRepository;
import nl.hu.cisq1.lingo.games.domain.Game;
import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
public class GameServiceIT {

    @Autowired
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("start a new game")
    void endpointNewGameTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .post("/game");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.score", is(0)))
                .andExpect(jsonPath("$.gameProgress", is("PLAYING")));

    }
    @Test
    @DisplayName("cannot start new round if game not found")
    void cannotStartRound() throws Exception {
        var game = new Game();
        var gameRepository = mock(GameRepository.class);
        var wordRepository = mock(SpringWordRepository.class);

        game.newRound("baard");

        when(gameRepository.findById(0L))
                .thenReturn(Optional.of(game));

        when(wordRepository.findRandomWordByLength(6))
                .thenReturn(Optional.of(new Word("hoeden")));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/games/0/newRound");

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("cannot get a game by id if id does not exist")
    void getGame() throws Exception {
        var game = new Game();
        var gameRepository = mock(GameRepository.class);
        var wordRepository = mock(SpringWordRepository.class);

        game.newRound("baard");

        when(gameRepository.findById(0L))
                .thenReturn(Optional.of(game));

        when(wordRepository.findRandomWordByLength(6))
                .thenReturn(Optional.of(new Word("hoeden")));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/games/0");

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

}

