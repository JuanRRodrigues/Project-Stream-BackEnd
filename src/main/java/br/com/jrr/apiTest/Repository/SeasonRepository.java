package br.com.jrr.apiTest.Repository;



import br.com.jrr.apiTest.domain.Season.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeasonRepository extends JpaRepository<Season, UUID> {

}
