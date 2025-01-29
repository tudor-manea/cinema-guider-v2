package com.tmanea.backend.dto;

import lombok.Data;

@Data
public class TmdbMovieDto {
    private Long id;
    private String title;
    private String releaseDate;
    private Double voteAverage;
    private String overview;
    private String posterPath;
    double popularity;
    double voteCount;
    String originalLanguage;
    String originalTitle;
}