package nl.hu.cisq1.lingo.games.presentation;

import lombok.NoArgsConstructor;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.games.application.GameService;
import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
public class GameControllerIT {

    @Autowired
    private MockMvc mockMvc;


//    @Test
//    @DisplayName("start a new game")
//    void startNewGame() throws Exception {
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/game");
//
//        mockMvc.perform(request)
//                .andExpect(jsonPath("$.id", greaterThanOrEqualTo(0)));
//    }


}