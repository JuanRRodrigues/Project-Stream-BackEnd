package br.com.jrr.apiTest.Repository;


import br.com.jrr.apiTest.domain.Movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.rmi.server.UID;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
