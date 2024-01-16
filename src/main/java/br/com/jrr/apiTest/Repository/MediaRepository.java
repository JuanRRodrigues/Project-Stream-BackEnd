package br.com.jrr.apiTest.Repository;


import br.com.jrr.apiTest.domain.Media.Media;
import br.com.jrr.apiTest.domain.Movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
}
