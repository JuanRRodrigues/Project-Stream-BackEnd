package br.com.jrr.apiTest.domain.Season;


import br.com.jrr.apiTest.domain.Episode.DataEpisode;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeason(@JsonAlias("Season")Integer numberSEASON,
                         @JsonAlias("Title")String media,
                         @JsonAlias("Episodes")List<DataEpisode> episodes) {
}
