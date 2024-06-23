package br.com.jrr.apiTest.domain.Season;

import br.com.jrr.apiTest.domain.Serie.Serie;
import br.com.jrr.apiTest.domain.Episode.Episode;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "seasons")
@Data
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;

    private Integer numberSEASON;

    private String media;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Episode> episodes;



    public Season(Serie serie, DataSeason dataSeason) {
        this.serie = serie;
        this.numberSEASON = dataSeason.numberSEASON();
        this.media = dataSeason.media();
    }

    public Season() {

    }

    // Método addEpisode
    public void addEpisode(Episode episode) {
        // Certifique-se de que a lista de episódios não é nula antes de adicioná-la
        if (this.episodes == null) {
            this.episodes = new ArrayList<>();
        }

        this.episodes.add(episode);
    }

    public void removeEpisode(Episode episode) {
        episodes.remove(episode);
        episode.setSeason(null);
    }

}
