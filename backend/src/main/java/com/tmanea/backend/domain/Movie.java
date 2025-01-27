package com.tmanea.backend.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.Entity;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String releaseDate; // format "yyyy-MM-dd"
    private Double rating; // 0.0 (worst) to 10.0 (best)
    private String summary;
    private String posterPath;
    double popularity;
    double voteCount;
    String originalLanguage;
    String originalTitle;
}