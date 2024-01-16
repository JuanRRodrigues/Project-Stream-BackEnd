package br.com.jrr.apiTest.service;

import br.com.jrr.apiTest.Repository.MovieRepository;
import br.com.jrr.apiTest.domain.Movie.Movie;
import br.com.jrr.apiTest.domain.Movie.MovieDTO;
import br.com.jrr.apiTest.domain.Serie.Serie;
import br.com.jrr.apiTest.domain.Serie.SerieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;


    public List<MovieDTO> getMovies() {
        return movieRepository.findAll()
                .stream()
                .map(s -> new MovieDTO(s.getId(), s.getTitle(), s.getReleaseYear(), s.getType(), s.getPoster(), s.getRated(), s.getReleaseDate(), s.getRuntime(), s.getGenre(), s.getDescription(), s.getLanguage(),s.getCountry(),s.getAwards()))
                .collect(Collectors.toList())
                ;


    }

    public MovieDTO getById(UUID id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if(movie.isPresent()){
            Movie s = movie.get();
            return new MovieDTO(s.getId(), s.getTitle(), s.getReleaseYear(), s.getType(), s.getPoster(), s.getRated(), s.getReleaseDate(), s.getRuntime(), s.getGenre(), s.getDescription(), s.getLanguage(),s.getCountry(),s.getAwards());
        }
        return null;

    }
}
