package com.tmanea.backend.dto;

import lombok.Data;

@Data
public class TmdbMovieDto {
    private Long id;
    private String title;
    private String release_date;
    private float vote_average;
    private String overview;
    private String posterPath;
    double popularity;
    double voteCount;
    String originalLanguage;
    String originalTitle;
}