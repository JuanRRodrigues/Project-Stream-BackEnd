package br.com.jrr.apiTest.Repository;


import br.com.jrr.apiTest.domain.Serie.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SerieRepository extends JpaRepository<Serie, UUID> {
}
