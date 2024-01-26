package br.com.jrr.apiTest.service;

import br.com.jrr.apiTest.Repository.MovieRepository;
import br.com.jrr.apiTest.domain.API.DataMediaAPI;
import br.com.jrr.apiTest.domain.API.DataMediaRegistrationAPI;
import br.com.jrr.apiTest.domain.Enums.Category;
import br.com.jrr.apiTest.domain.Media.Media;
import br.com.jrr.apiTest.domain.Movie.Movie;
import br.com.jrr.apiTest.domain.Movie.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    private final GetData get = new GetData();
    private final ConvertData convert = new ConvertData();
    private final String LINK = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=37a01a66";

    public List<MovieDTO> getMovies() {
        return movieRepository.findAll()
                .stream()
                .map(s -> new MovieDTO(s.getId(), s.getTitle(), s.getReleaseYear(), s.getType(), s.getPoster(), s.getRated(), s.getReleaseDate(), s.getRuntime(), s.getGenre(), s.getDescription(), s.getLanguage(), s.getCountry(), s.getAwards()))
                .collect(Collectors.toList());
    }

    public MovieDTO getById(UUID id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        return optionalMovie.map(s -> new MovieDTO(s.getId(), s.getTitle(), s.getReleaseYear(), s.getType(), s.getPoster(), s.getRated(), s.getReleaseDate(), s.getRuntime(), s.getGenre(), s.getDescription(), s.getLanguage(), s.getCountry(), s.getAwards()))
                .orElse(null);
    }

    public MovieDTO registerByAPI(DataMediaRegistrationAPI data) {

        String mediaTitle = data.title();
        Category mediaType = data.category();


        if (mediaTitle != null) {
            try {
                var json = get.obterDados(LINK + mediaTitle.replace(" ", "+") + API_KEY);
                DataMediaAPI dataMediaAPI = convert.getDate(json, DataMediaAPI.class);

                var media = new Media(dataMediaAPI);

                if (mediaType == Category.Movie && Objects.equals(media.getType(), "movie")) {
                    var movie = new Movie(dataMediaAPI);
                    Movie savedMovie = movieRepository.save(movie);

                    return new MovieDTO(savedMovie.getId(), savedMovie.getTitle(), savedMovie.getReleaseYear(), savedMovie.getType(), savedMovie.getPoster(), savedMovie.getRated(), savedMovie.getReleaseDate(), savedMovie.getRuntime(), savedMovie.getGenre(), savedMovie.getDescription(), savedMovie.getLanguage(), savedMovie.getCountry(), savedMovie.getAwards());
                }
            } catch (Exception e) {
                // Lidar com exceções (pode logar ou lançar outra exceção, dependendo dos requisitos)
                e.printStackTrace();
            }
        }
        return null;
    }
}
