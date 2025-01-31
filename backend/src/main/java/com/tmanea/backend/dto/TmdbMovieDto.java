package com.tmanea.backend.dto;

import lombok.Data;

@Data
public class TmdbMovieDto {
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