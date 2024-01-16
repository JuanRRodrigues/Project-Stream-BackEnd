package br.com.jrr.apiTest.controller;


import br.com.jrr.apiTest.Repository.*;
import br.com.jrr.apiTest.domain.Serie.Serie;
import br.com.jrr.apiTest.domain.Enums.Category;
import br.com.jrr.apiTest.domain.Episode.DataEpisode;
import br.com.jrr.apiTest.domain.Episode.Episode;
import br.com.jrr.apiTest.domain.Media.Media;
import br.com.jrr.apiTest.domain.API.DataMediaAPI;
import br.com.jrr.apiTest.domain.Media.MediaEditData;
import br.com.jrr.apiTest.domain.API.DataMediaRegistrationAPI;
import br.com.jrr.apiTest.domain.Movie.Movie;
import br.com.jrr.apiTest.domain.Season.DataSeason;
import br.com.jrr.apiTest.domain.Season.Season;
import br.com.jrr.apiTest.service.ConvertData;
import br.com.jrr.apiTest.service.GetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("movies")
public class ApiController {

    //Dados para acessar a API
    private GetData get = new GetData();
    ConvertData convert = new ConvertData();
    private final String LINK = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=37a01a66";


    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private SerieRepository serieRepository;
    @Autowired
    private MediaRepository mediaRepository;

    // Editar filme e serie (apenas a classe movie)
    @GetMapping("/editFormMovie")
    public String loadingPageFormMovies(@RequestParam(required = false) UUID id, Model model) {
        if (id != null) {
            Optional<Movie> optionalMovie = movieRepository.findById(id);
            if (optionalMovie.isPresent()) {
                model.addAttribute("movie", optionalMovie.get());
            } else {
                // Handle the case where the movie with the given ID is not found
                return "error";
            }
        }
        return "movies/editFormMovie";
    }


    @GetMapping("/editFormSerie")
    public String loadingPageFormSerie(@RequestParam(required = false) UUID id, Model model) {
        if (id != null) {
            Optional<Serie> optionalSerie = serieRepository.findById(id);
            if (optionalSerie.isPresent()) {
                model.addAttribute("serie", optionalSerie.get());
            } else {
                // Handle the case where the serie with the given ID is not found
                return "error";
            }
        }
        return "movies/editFormSerie";
    }



    // Listar Season de determinada serie
    @GetMapping("/seasonList")
    public String loadingPageSeason(@RequestParam UUID id, Model model) {
        Serie serie = serieRepository.findById(id).orElse(null);

        if (serie != null) {
            model.addAttribute("serie", serie);
            model.addAttribute("seasonList", serie.getSeasons());
            return "movies/seasonList";
        } else {
            return "redirect:/movies";
        }
    }

    // Listar episodios de determinada season
    @GetMapping("/episodeList")
    public String loadingPageEpisode(@RequestParam UUID id, Model model) {
        Season season = seasonRepository.findById(id).orElse(null);

        if (season != null) {
            model.addAttribute("season", season);
            model.addAttribute("episodeList", season.getEpisodes());
            return "movies/episodeList";
        } else {
            return "redirect:/movies";
        }
    }

    // formulario para pesquisar uma API pelo front
    @GetMapping("/form")
    public String pageForm() {
        return "movies/form";
    }

    // listar series e filmes

    @GetMapping
    public String loadingPageListing(Model model){
        model.addAttribute("listMovie", movieRepository.findAll());
        model.addAttribute("listSerie", serieRepository.findAll());
        model.addAttribute("listMedias", mediaRepository.findAll());
        return "movies/listing";
    }

    // receber os dados da API em json e converter para nosso banco (Serie e Filmes foram colocados em uma só
    // classe, pois a unica diferença é se tem seasons, o restos dos campos são iguais)
    @PostMapping()
    @Transactional
    public String registerMedia(DataMediaRegistrationAPI data) {
        String mediaTitle = data.title();
        Category mediaType = data.category();
        System.out.println(mediaTitle + mediaType);

        if (mediaTitle != null) {
            var json = get.obterDados(LINK + mediaTitle.replace(" ", "+") + API_KEY);
            DataMediaAPI dataMediaAPI = convert.getDate(json, DataMediaAPI.class);

            // Crie uma instância de Media usando os dados comuns
            var media = new Media(dataMediaAPI);

            // Salve a instância de Media no repositório MediaRepository
          //  mediaRepository.save(media);

            if (mediaType == Category.Movie && Objects.equals(media.getType(), "movie")) {
                // Se for um filme, crie e salve uma instância de Movie
                var movie = new Movie(dataMediaAPI);
                movieRepository.save(movie);
            } else if (mediaType == Category.Series && Objects.equals(media.getType(), "series")) {
                // Se for uma série, crie e salve uma instância de Serie
                var serie = new Serie(dataMediaAPI);
                serieRepository.save(serie);

                // Adicione temporadas e episódios conforme necessário
                List<DataSeason> seasons = new ArrayList<>();
                for (int i = 1; i <= dataMediaAPI.totalSeasons(); i++) {
                    json = get.obterDados(LINK + mediaTitle.replace(" ", "+") + "&season=" + i + API_KEY);
                    DataSeason dataSeason = convert.getDate(json, DataSeason.class);
                    seasons.add(dataSeason);

                    // Criar e salvar a temporada
                    var season = new Season(serie, dataSeason);
                    seasonRepository.save(season);

                    // Criar e salvar os episódios para a temporada
                    List<DataEpisode> dataEpisodes = dataSeason.episodes();
                    for (DataEpisode dataEpisode : dataEpisodes) {
                        Episode episode = new Episode(season, dataEpisode);
                        episode.setSerieName(serie.getTitle());
                        episodeRepository.save(episode);
                        season.addEpisode(episode); // Mantenha a consistência
                    }
                }
            }
        }
        return "redirect:/movies";
    }

    // Usado para controle de qual dados podem ser editados
    @PutMapping
    @Transactional
    public String editMedia(MediaEditData data){
        var movie = movieRepository.getReferenceById(data.id());
        movie.mediaEditData(data);

        return "redirect:/movies";
    }

    // Delete com base no ID apenas a classe serie;
    @DeleteMapping
    @Transactional
    public String delete(UUID id, String type){
        if (Objects.equals(type, "movie")){
            movieRepository.deleteById(id);
        }else if(Objects.equals(type, "series")){
            serieRepository.deleteById(id);
        }
        return "redirect:/movies";
    }

}
