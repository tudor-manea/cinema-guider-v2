package com.tmanea.backend.service;

import com.tmanea.backend.domain.Movie;
import com.tmanea.backend.dto.ScrapedMovieDto;
import com.tmanea.backend.dto.TmdbMovieDto;
import com.tmanea.backend.dto.TmdbMoviePageDto;
import com.tmanea.backend.mapper.MovieMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ActiveMoviesSyncService {
    private final MovieScraperService scraperService;
    private final TmdbMovieService tmdbService;
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    public ActiveMoviesSyncService(
            MovieScraperService scraperService,
            TmdbMovieService tmdbService,
            MovieService movieService,
            MovieMapper movieMapper) {
        this.scraperService = scraperService;
        this.tmdbService = tmdbService;
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

//    @Scheduled(cron = "* * * * * *") // Run at midnight every day
//    @Transactional
//    public void syncActiveMovies() {
//        log.info("Starting active movies sync");
//        try {
//            // Step 1: Get current movies from cinema website
//            List<ScrapedMovieDto> scrapedMovies = scraperService.scrapeMovies();
//            Set<String> activeMovieTitles = scrapedMovies.stream()
//                    .map(ScrapedMovieDto::getTitle)
//                    .collect(Collectors.toSet());
//
//            // Step 2: Process each scraped movie
//            List<Movie> updatedMovies = new ArrayList<>();
//            for (ScrapedMovieDto scrapedMovie : scrapedMovies) {
//                try {
//                    TmdbMoviePageDto tmdbResult = tmdbService.searchMovies(scrapedMovie.getTitle());
//                    if (tmdbResult.getResults() != null && !tmdbResult.getResults().isEmpty()) {
//                        TmdbMovieDto tmdbMovie = tmdbResult.getResults().getFirst();
//                        Movie movie = movieMapper.toMovie(tmdbMovie, scrapedMovie);
//                        updatedMovies.add(movie);
//                    }
//                } catch (Exception e) {
//                    log.error("Error processing movie {}: {}", scrapedMovie.getTitle(), e.getMessage());
//                }
//            }
//
//            // Step 3: Save all updated movies
//            movieService.saveMovies(updatedMovies);
//
//            // Step 4: Remove movies that are no longer active
//            List<Movie> existingMovies = movieService.getAllMovies();
//            for (Movie movie : existingMovies) {
//                if (!activeMovieTitles.contains(movie.getTitle())) {
//                    movieService.deleteMovie(movie.getId());
//                    log.info("Deleted inactive movie: {}", movie.getTitle());
//                }
//            }
//
//            log.info("Completed active movies sync. Updated {} movies", updatedMovies.size());
//        } catch (IOException e) {
//            log.error("Failed to sync active movies", e);
//        }
//    }
}