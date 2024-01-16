package br.com.jrr.apiTest.controller;

import br.com.jrr.apiTest.domain.Serie.SerieDTO;
import br.com.jrr.apiTest.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/series")
public class SerieController {
    
@Autowired
private SerieService service;

    @GetMapping("/list")
    public List<SerieDTO> getSeries() {
        return service.getSeries();
    }

    @GetMapping("/{id}")
    public SerieDTO getById(@PathVariable UUID id){
            return service.getById(id);
    }


}
