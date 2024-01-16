package br.com.jrr.apiTest.domain.Serie;


import br.com.jrr.apiTest.domain.Media.Media;
import br.com.jrr.apiTest.domain.API.DataMediaAPI;
import br.com.jrr.apiTest.domain.Media.MediaEditData;
import br.com.jrr.apiTest.domain.Season.Season;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "series")
@Getter
@DiscriminatorValue("TYPE_MEDIA")
public class Serie extends Media {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer totalSeasons;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Season> seasons;



    public Serie(DataMediaAPI dataMediaAPI) {
        super(dataMediaAPI);
        this.totalSeasons = dataMediaAPI.totalSeasons();
    }

    public Serie(){}


    public void serieEditData(MediaEditData data) {
        super.mediaEditData(data);
    }


}
