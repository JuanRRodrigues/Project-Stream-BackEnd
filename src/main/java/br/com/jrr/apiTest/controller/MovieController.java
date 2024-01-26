package br.com.jrr.apiTest.controller;

import br.com.jrr.apiTest.Repository.MovieRepository;
import br.com.jrr.apiTest.Repository.SerieRepository;
import br.com.jrr.apiTest.domain.API.DataMediaRegistrationAPI;
import br.com.jrr.apiTest.domain.Enums.Category;
import br.com.jrr.apiTest.domain.Media.MediaEditData;
import br.com.jrr.apiTest.domain.Movie.Movie;
import br.com.jrr.apiTest.domain.Movie.MovieDTO;
import br.com.jrr.apiTest.domain.Serie.SerieDTO;
import br.com.jrr.apiTest.service.MovieService;
import br.com.jrr.apiTest.service.SerieService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/movies")
public class MovieController {

@Autowired
private MovieService service;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/list")
    public List<MovieDTO> getMovies() {
        return service.getMovies();
    }


    @GetMapping("/{id}")
    public MovieDTO getById(@PathVariable UUID id){
        return service.getById(id);
    }

    @PostMapping("/post")
    public MovieDTO postByAPI(@RequestBody @Valid DataMediaRegistrationAPI data, UriComponentsBuilder uriBuilder){
        return service.registerByAPI(data);
    }

    @PutMapping("/edit")
    public MovieDTO EditSerie(@RequestBody @Valid MediaEditData data){
        var movie = movieRepository.getReferenceById(data.id());
        movie.movieEditData(data);
        movieRepository.save(movie);
        return service.getById(data.id());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public MovieDTO delete(@PathVariable UUID id){
        movieRepository.deleteById(id);
        return service.getById(id);
    }


}
