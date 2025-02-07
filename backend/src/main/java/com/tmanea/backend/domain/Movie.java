package com.tmanea.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String release_date;
    private float vote_average;
    @Lob
    @Column
    private String overview;
    private String poster_path;
    private float popularity;
    private double vote_count;
    private String original_language;
    private String original_title;
    private boolean seen = false;
}