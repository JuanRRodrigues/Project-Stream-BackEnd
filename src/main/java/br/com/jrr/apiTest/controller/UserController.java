package br.com.jrr.apiTest.controller;


import br.com.jrr.apiTest.Repository.*;
import br.com.jrr.apiTest.domain.Serie.Serie;
import br.com.jrr.apiTest.domain.Season.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("users")
public class UserController {



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


    // Listar Season de determinada serie
    @GetMapping("/seasonList")
    public String loadingPageSeason(@RequestParam UUID id, Model model) {
        Serie serie = serieRepository.findById(id).orElse(null);

        if (serie != null) {
            model.addAttribute("serie", serie);
            model.addAttribute("seasonList", serie.getSeasons());
            return "movies/seasonList";
        } else {
            return "redirect:/users";
        }
    }

    // Listar episodios de determinada season
    @GetMapping("/episodeList")
    public String loadingPageEpisode(@RequestParam UUID id, Model model) {
        Season season = seasonRepository.findById(id).orElse(null);

        if (season != null) {
            model.addAttribute("season", season);
            model.addAttribute("episodeList", season.getEpisodes());
            return "users/episodeList";
        } else {
            return "redirect:/users";
        }
    }


    @GetMapping
    public String loadingPageListing(Model model){
        model.addAttribute("listMovie", movieRepository.findAll());
        model.addAttribute("listSerie", serieRepository.findAll());
        model.addAttribute("listMedias", mediaRepository.findAll());
        return "users/MainPage";
    }


}
