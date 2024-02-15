package br.com.jrr.apiTest.domain.Movie;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;


public record MovieDTO(UUID id,
                      String title,
                       String releaseYear,
                       String type,
                       String poster,
                       String rated,
                       String releaseDate,
                       String runtime,
                       String genre,
                       String description,
                       String language,
                       String country ,
                       String awards

) {


}
