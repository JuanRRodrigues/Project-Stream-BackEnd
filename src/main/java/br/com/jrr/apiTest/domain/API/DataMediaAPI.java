package br.com.jrr.apiTest.domain.API;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DataMediaAPI(Long id,
                           @JsonAlias("Title")String title,
                           @JsonAlias("Year")String releaseYear,
                           @JsonAlias("Type")String type,
                           @JsonAlias("Poster") String poster,
                           @JsonAlias("totalSeasons")Integer totalSeasons,
                           @JsonAlias("Rated") String rated,
                           @JsonAlias("Released") String releaseDate,
                           @JsonAlias("Runtime") String runtime,
                           @JsonAlias("Genre") String genre,
                           @JsonAlias("Director") String director,
                           //    @JsonAlias("Writer") String Author,
                           @JsonAlias("Actors") String actores,
                           @JsonAlias("Plot") String description,
                           @JsonAlias("Language") String language,
                           @JsonAlias("Country")String country ,
                           @JsonAlias("Awards")String awards,
                           //   @JsonAlias("Poster")String poster,
                           //@JsonAlias("Ratings") List<String> ratings,
                           @JsonAlias("BoxOffice")String boxOffice ,
                           @JsonAlias("Production")String production){
                     //   @JsonAlias("Website")String website){

}
