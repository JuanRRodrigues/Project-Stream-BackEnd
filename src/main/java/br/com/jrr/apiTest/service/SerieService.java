package br.com.jrr.apiTest.service;

import br.com.jrr.apiTest.Repository.SerieRepository;
import br.com.jrr.apiTest.domain.Serie.Serie;
import br.com.jrr.apiTest.domain.Serie.SerieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;


    public List<SerieDTO> getSeries() {
        return serieRepository.findAll()
                .stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitle(), s.getReleaseYear(), s.getType(), s.getPoster(), s.getTotalSeasons(), s.getRated(), s.getReleaseDate(), s.getRuntime(), s.getGenre(), s.getDescription(), s.getLanguage(),s.getCountry(),s.getAwards()))
                .collect(Collectors.toList())
                ;


    }

    public SerieDTO getById(UUID id) {
        Optional<Serie> serie = serieRepository.findById(id);
            if(serie.isPresent()){
                Serie s = serie.get();
                return new SerieDTO(s.getId(), s.getTitle(), s.getReleaseYear(), s.getType(), s.getPoster(), s.getTotalSeasons(), s.getRated(), s.getReleaseDate(), s.getRuntime(), s.getGenre(), s.getDescription(), s.getLanguage(),s.getCountry(),s.getAwards());
            }
            return null;

    }
}
