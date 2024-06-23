package br.com.jrr.apiTest.controller;

import br.com.jrr.apiTest.Repository.SerieRepository;
import br.com.jrr.apiTest.domain.API.DataMediaAPI;
import br.com.jrr.apiTest.domain.API.DataMediaRegistrationAPI;
import br.com.jrr.apiTest.domain.Enums.Category;
import br.com.jrr.apiTest.domain.Media.MediaEditData;
import br.com.jrr.apiTest.domain.Movie.MovieDTO;
import br.com.jrr.apiTest.domain.Serie.SerieDTO;
import br.com.jrr.apiTest.service.ConvertData;
import br.com.jrr.apiTest.service.GetData;
import br.com.jrr.apiTest.service.SerieService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("api/v1/series")
public class SerieController {
    
@Autowired
private SerieService service;

    @Autowired
    private SerieRepository serieRepository;


    @GetMapping("/list")
    public List<SerieDTO> getSeries() {
        return service.getSeries();
    }

    @GetMapping("/{id}")
    public SerieDTO getById(@PathVariable UUID id){
            return service.getById(id);
    }

    @PostMapping("/post")
    public SerieDTO postByAPI(@RequestBody DataMediaRegistrationAPI data, UriComponentsBuilder uriBuilder){
        return service.registerByAPI(data);
    }

    @PutMapping("/edit/{id}")
    public SerieDTO EditSerie(@RequestBody @Valid MediaEditData data){
        var serie = serieRepository.getReferenceById(data.id());
        serie.serieEditData(data);
        System.out.println(serie);
        serieRepository.save(serie);
        return service.getById(data.id());
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public SerieDTO delete(@PathVariable UUID id){
        serieRepository.deleteById(id);
        return service.getById(id);
    }

}
