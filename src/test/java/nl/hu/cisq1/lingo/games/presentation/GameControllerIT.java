package nl.hu.cisq1.lingo.games.presentation;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.games.data.GameRepository;
import nl.hu.cisq1.lingo.games.domain.Game;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class GameControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpringWordRepository wordRepository;

    @MockBean
    private GameRepository gameRepository;

    @Test
    @DisplayName("start a new game")
    void endpointNewGameTest() throws Exception{
        //Given
        RequestBuilder request = MockMvcRequestBuilders
                .post("/game");
        //When
        when(wordRepository.findRandomWordByLength(5))
                .thenReturn(Optional.of(new Word("baard")));
        //Then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score", is(0)))
                .andExpect(jsonPath("$.gameProgress", is("PLAYING")));

    }

    @Test
    @DisplayName("start a new round")
    void startNewRound() throws Exception {
        //Given
        Game game = new Game();
        game.newRound("baard");
        game.gameAttempt("baard");

        //When
        when(gameRepository.findById(0L))
                .thenReturn(Optional.of(game));

        when(wordRepository.findRandomWordByLength(6))
                .thenReturn(Optional.of(new Word("hoeden")));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/game/0/nextRound");
        
        //Then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameProgress", is("PLAYING")))
                .andExpect(jsonPath("$.score", is(45)));
    }


    @Test
    @DisplayName("cannot start new round if game not found")
    void cannotStartRound() throws Exception {
        //Given
        var game = new Game();;
        game.newRound("baard");

        //When
        when(gameRepository.findById(0L))
                .thenReturn(Optional.of(game));

        when(wordRepository.findRandomWordByLength(6))
                .thenReturn(Optional.of(new Word("hoeden")));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/game/0/newRound");

        //Then
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("cannot get a game by id if id does not exist")
    void getGame() throws Exception {
        //Given
        var game = new Game();

        //When
        when(gameRepository.findById(0L))
                .thenReturn(Optional.of(game));

        when(wordRepository.findRandomWordByLength(6))
                .thenReturn(Optional.of(new Word("hoeden")));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/game/0");

        //Then
        mockMvc.perform(request)
                .andExpect(status().is5xxServerError());
    }

}

