package com.tmanea.backend.mapper;

import com.tmanea.backend.domain.Movie;
import com.tmanea.backend.dto.ScrapedMovieDto;
import com.tmanea.backend.dto.TmdbMovieDto;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public Movie toMovie(TmdbMovieDto tmdbMovie, ScrapedMovieDto scrapedMovie) {
        Movie movie = new Movie();
        movie.setTitle(tmdbMovie.getTitle());
        movie.setRating(tmdbMovie.getVote_average());
        movie.setReleaseDate(tmdbMovie.getRelease_date());
        movie.setSummary(tmdbMovie.getOverview());
        movie.setPosterPath(tmdbMovie.getPosterPath());
        movie.setPopularity(tmdbMovie.getPopularity());
        movie.setVoteCount(tmdbMovie.getVoteCount());
        movie.setOriginalLanguage(tmdbMovie.getOriginalLanguage());
        movie.setOriginalTitle(tmdbMovie.getOriginalTitle());
        return movie;
    }
}
