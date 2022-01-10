package nl.hu.cisq1.lingo.games.application;

import nl.hu.cisq1.lingo.LingoApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("ci")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = LingoApplication.class)
public class GameServiceIT {
}
