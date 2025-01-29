package com.tmanea.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class TmdbMoviePageDto {
    private int page;
    private int totalPages;
    private int totalResults;
    private List<TmdbMovieDto> results;
}
