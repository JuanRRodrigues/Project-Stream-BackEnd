package br.com.jrr.apiTest.service;

import br.com.jrr.apiTest.Repository.EpisodeRepository;
import br.com.jrr.apiTest.Repository.SeasonRepository;
import br.com.jrr.apiTest.Repository.SerieRepository;
import br.com.jrr.apiTest.domain.API.DataMediaAPI;
import br.com.jrr.apiTest.domain.API.DataMediaRegistrationAPI;
import br.com.jrr.apiTest.domain.Enums.Category;
import br.com.jrr.apiTest.domain.Episode.DataEpisode;
import br.com.jrr.apiTest.domain.Episode.Episode;
import br.com.jrr.apiTest.domain.Media.Media;
import br.com.jrr.apiTest.domain.Movie.Movie;
import br.com.jrr.apiTest.domain.Season.DataSeason;
import br.com.jrr.apiTest.domain.Season.Season;
import br.com.jrr.apiTest.domain.Serie.Serie;
import br.com.jrr.apiTest.domain.Serie.SerieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class SerieService {



    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private SerieRepository serieRepository;

    private final GetData get = new GetData();
    private final ConvertData convert = new ConvertData();
    private final String LINK = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=37a01a66";


    public List<SerieDTO> getSeries() {
        return serieRepository.findAll()
                .stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitle(),
                        s.getReleaseYear(),
                        s.getType(),
                        s.getPoster(),
                        s.getTotalSeasons(),
                        s.getRated(),
                        s.getReleaseDate(),
                        s.getRuntime(),
                        s.getGenre(),
                        s.getDescription(),
                        s.getLanguage(),
                        s.getCountry(),
                        s.getAwards()
                        ))
                .collect(Collectors.toList())
                ;

    }

    public SerieDTO getById(UUID id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(
                    s.getId(),
                    s.getTitle(),
                    s.getReleaseYear(),
                    s.getType(),
                    s.getPoster(),
                    s.getTotalSeasons(),
                    s.getRated(),
                    s.getReleaseDate(),
                    s.getRuntime(),
                    s.getGenre(),
                    s.getDescription(),
                    s.getLanguage(),
                    s.getCountry(),
                    s.getAwards()
                    );
        }
        return null;
    }

    public SerieDTO registerByAPI(DataMediaRegistrationAPI data) {

        String mediaTitle = data.title();
        Category mediaType = data.category();
        System.out.println(mediaTitle + mediaType);

        if (mediaTitle != null) {
            var json = get.obterDados(LINK + mediaTitle.replace(" ", "+") + API_KEY);
            DataMediaAPI dataMediaAPI = convert.getDate(json, DataMediaAPI.class);

            var media = new Media(dataMediaAPI);

            if (mediaType == Category.Series && Objects.equals(media.getType(), "series")) {
                // Se for uma série, crie e salve uma instância de Serie
                var serie = new Serie(dataMediaAPI);
                Serie savedSerie= serieRepository.save(serie);

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

                return new SerieDTO(
                        savedSerie.getId(),
                        savedSerie.getTitle(),
                        savedSerie.getReleaseYear(),
                        savedSerie.getType(),
                        savedSerie.getPoster(),
                        savedSerie.getTotalSeasons(),
                        savedSerie.getRated(),
                        savedSerie.getReleaseDate(),
                        savedSerie.getRuntime(),
                        savedSerie.getGenre(),
                        savedSerie.getDescription(),
                        savedSerie.getLanguage(),
                        savedSerie.getCountry(),
                        savedSerie.getAwards()
                        );
            }
        }
        return null;
    }


}
