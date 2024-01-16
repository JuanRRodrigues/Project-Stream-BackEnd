package br.com.jrr.apiTest.domain.Movie;


import br.com.jrr.apiTest.domain.API.DataMediaAPI;
import br.com.jrr.apiTest.domain.Media.Media;
import br.com.jrr.apiTest.domain.Media.MediaEditData;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "movies")
@Getter
@DiscriminatorValue("TYPE_MEDIA")
public class Movie extends Media {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String boxOffice;
    private String director;




    public Movie(DataMediaAPI dataMediaAPI) {
        super(dataMediaAPI);
        this.boxOffice = dataMediaAPI.boxOffice();
        this.director = dataMediaAPI.director();
    }

    public Movie(){}


    public void movieEditData(MediaEditData data) {
        super.mediaEditData(data);
        this.boxOffice = data.boxOffice();
        this.director = data.director();
    }


}
