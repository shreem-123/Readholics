package com.readholics.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity
@Table(name = "Genre")
public class Genre {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @NotBlank(message ="Genre Name required!")
    @Column(name = "genre_name")
    private String genreName;

    public Genre() {
    }

    public Genre(int genreId, String name) {
        this.genreName = name;
    }

    public void setName(String name) {
        this.genreName = name;
    }
}
