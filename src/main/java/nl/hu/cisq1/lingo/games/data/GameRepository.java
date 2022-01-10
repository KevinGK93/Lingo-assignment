package nl.hu.cisq1.lingo.games.data;

import nl.hu.cisq1.lingo.games.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
