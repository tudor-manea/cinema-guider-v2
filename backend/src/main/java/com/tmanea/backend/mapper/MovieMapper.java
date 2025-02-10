package com.tmanea.backend.mapper;

import com.tmanea.backend.domain.Movie;
import com.tmanea.backend.dto.LetterboxdMovieDto;
import com.tmanea.backend.dto.ScrapedMovieDto;
import com.tmanea.backend.dto.TmdbMovieDto;
import com.tmanea.backend.service.LetterboxdScraperService;
import com.tmanea.backend.service.TmdbMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MovieMapper {
    private static final Logger logger = LoggerFactory.getLogger(MovieMapper.class);

    private final TmdbMovieService tmdbService;
    private final LetterboxdScraperService letterboxdService;

    public MovieMapper(TmdbMovieService tmdbService, LetterboxdScraperService letterboxdService) {
        this.tmdbService = tmdbService;
        this.letterboxdService = letterboxdService;
    }

    public Movie tmdbToMovie(TmdbMovieDto tmdbMovie) {
        Movie movie = new Movie();
        movie.setTitle(tmdbMovie.getTitle());
//        movie.setVote_average(tmdbMovie.getVote_average());
        movie.setRelease_date(tmdbMovie.getRelease_date());
        movie.setPoster_path(tmdbMovie.getPoster_path());
        movie.setPopularity(tmdbMovie.getPopularity());
        movie.setOverview(tmdbMovie.getOverview());
        movie.setVote_count(tmdbMovie.getVote_count());
        movie.setOriginal_language(tmdbMovie.getOriginal_language());
        movie.setOriginal_title(tmdbMovie.getOriginal_title());

        try {
            LetterboxdMovieDto letterboxdMovie = letterboxdService.tryUrls(tmdbMovie.getTitle(), tmdbMovie.getRelease_date());
            if (letterboxdMovie != null) {
                movie.setVote_average(letterboxdMovie.getRating());
                logger.info("Successfully mapped Letterbox rating {} for movie: {}",
                        letterboxdMovie.getRating(), tmdbMovie.getTitle());
                return movie;
            }
            logger.warn("No Letterboxd rating found for movie: {}, skipping", tmdbMovie.getTitle());
            return null;
        } catch (IOException e) {
            logger.error("Error getting Letterboxd rating for {}, skipping", tmdbMovie.getTitle());
            return null;
        }
    }

    public TmdbMovieDto scrapedToTmdbMovie(ScrapedMovieDto scrapedMovie) {
        return tmdbService.searchMovies(scrapedMovie.getTitle()).getResults().getFirst();
    }
}
