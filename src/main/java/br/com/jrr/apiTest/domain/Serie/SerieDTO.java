package br.com.jrr.apiTest.domain.Serie;

import br.com.jrr.apiTest.domain.Season.Season;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;


public record SerieDTO(UUID id,
                       String title,

                       String releaseYear,
                       String type,
                       String poster,
                       Integer totalSeasons,
                       String rated,
                       String releaseDate,
                       String runtime,
                       String genre,
                       String description,
                       String language,
                       String country ,
                       String awards

                  ){
                     //   @JsonAlias("Website")String website){

}
