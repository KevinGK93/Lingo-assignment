package nl.hu.cisq1.lingo.games.data;

import nl.hu.cisq1.lingo.games.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findById(long id);
}
