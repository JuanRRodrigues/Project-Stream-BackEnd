package br.com.jrr.apiTest.domain.Media;


import br.com.jrr.apiTest.domain.Enums.Category;
import br.com.jrr.apiTest.domain.API.DataMediaAPI;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
@Table(name = "medias")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "type_entity", discriminatorType = DiscriminatorType.STRING)

public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private Category category;
    private String type;
    private String releaseYear;
    private String description;
    private String rated;
    private String releaseDate;
    private String runtime;
    private String genre;
    private String actores;
    private String poster;
    private String language;
    private String country;
    private String awards;
    private String production;




    public Media(DataMediaAPI dataMediaAPI) {
        this.title = dataMediaAPI.title();
        this.releaseYear = dataMediaAPI.releaseYear();
        this.type = dataMediaAPI.type();
        this.poster = dataMediaAPI.poster();
        this.description = dataMediaAPI.description();
        this.rated = dataMediaAPI.rated();
        this.releaseDate = dataMediaAPI.releaseDate();
        this.runtime = dataMediaAPI.runtime();
        this.genre = dataMediaAPI.genre();
        this.actores = dataMediaAPI.actores();
        this.language = dataMediaAPI.language();
        this.country = dataMediaAPI.country();
        this.awards = dataMediaAPI.awards();
        this.production = dataMediaAPI.production();
    }

    public Media(){}



    @Override
    public String toString() {
        return "Media{" +
                "title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", description='" + description + '\'' +
                '}';
    }
  public void mediaEditData(MediaEditData data) {
      this.title = data.title();
      this.releaseYear = data.releaseYear();
      this.type = data.type();
      this.poster = data.poster();
      this.description = data.description();
      this.rated = data.rated();
      this.releaseDate = data.releaseDate();
      this.runtime = data.runtime();
      this.genre = data.genre();
      this.actores = data.actores();
      this.language = data.language();
      this.country = data.country();
      this.awards = data.awards();
      this.production = data.production();
  }


}
