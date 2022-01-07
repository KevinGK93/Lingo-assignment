package nl.hu.cisq1.lingo.games.application;

import lombok.RequiredArgsConstructor;
import nl.hu.cisq1.lingo.games.data.GameRepository;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    private final WordService wordService;
    private final GameRepository gameRepository;
}
