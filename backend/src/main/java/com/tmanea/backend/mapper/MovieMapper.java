package com.tmanea.backend.mapper;

import com.tmanea.backend.domain.Movie;
import com.tmanea.backend.dto.ScrapedMovieDto;
import com.tmanea.backend.dto.TmdbMovieDto;
import com.tmanea.backend.service.TmdbMovieService;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    private final TmdbMovieService tmdbService;

    public MovieMapper(TmdbMovieService tmdbService) {
        this.tmdbService = tmdbService;
    }

    public Movie tmdbToMovie(TmdbMovieDto tmdbMovie) {
        Movie movie = new Movie();
        movie.setTitle(tmdbMovie.getTitle());
        movie.setVote_average(tmdbMovie.getVote_average());
        movie.setRelease_date(tmdbMovie.getRelease_date());
        movie.setPoster_path(tmdbMovie.getPoster_path());
        movie.setPopularity(tmdbMovie.getPopularity());
        movie.setVote_count(tmdbMovie.getVote_count());
        movie.setOriginal_language(tmdbMovie.getOriginal_language());
        movie.setOriginal_title(tmdbMovie.getOriginal_title());
        return movie;
    }

    public TmdbMovieDto scrapedToTmdbMovie(ScrapedMovieDto scrapedMovie) {
        return tmdbService.searchMovies(scrapedMovie.getTitle()).getResults().getFirst();
    }
}
