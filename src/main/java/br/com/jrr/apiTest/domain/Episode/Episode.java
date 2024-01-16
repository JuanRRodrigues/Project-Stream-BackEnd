package br.com.jrr.apiTest.domain.Episode;


import br.com.jrr.apiTest.domain.Season.Season;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.UUID;


@Entity
@Data
@Table(name = "episodes")
@Getter
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

        @ManyToOne
        @JoinColumn(name = "season_id", nullable = false)
        private Season season;
        private String title;
        private Integer numberEpisode;
        private double review;
        private LocalDate dateReleased;
        private String serieName;




    public Episode(Season season, DataEpisode dataEpisode) {
        this.season = season;
        this.title = dataEpisode.title();
        this.numberEpisode = dataEpisode.numberEpisode();
        try{
            this.review = Double.parseDouble(dataEpisode.review()); // valueOf
        }catch (NumberFormatException ex){
            this.review = 0.0;
        }
        try{
            this.dateReleased = LocalDate.parse(dataEpisode.dateReleased());
        } catch (DateTimeException ex){
            this.dateReleased = null;
        }

    }

    public Episode() {
    }

}
