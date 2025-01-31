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
    private String release_date;
    private float vote_average;
    private String overview;
    private String poster_path;
    private float popularity;
    private double vote_count;
    private String original_language;
    private String original_title;
}