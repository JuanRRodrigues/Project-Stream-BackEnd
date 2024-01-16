package br.com.jrr.apiTest.Repository;


import br.com.jrr.apiTest.domain.Episode.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EpisodeRepository extends JpaRepository<Episode, UUID> {
}
