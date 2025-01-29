package com.tmanea.backend.controller;

import com.tmanea.backend.dto.ScrapedMovieDto;
import com.tmanea.backend.dto.TmdbMovieDto;
import com.tmanea.backend.dto.TmdbMoviePageDto;
import com.tmanea.backend.mapper.MovieMapper;
import com.tmanea.backend.service.MovieScraperService;
import com.tmanea.backend.service.TmdbMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final MovieScraperService scraperService;
    private final TmdbMovieService tmdbService;
    private final MovieMapper movieMapper;

    public TestController(MovieScraperService scraperService, TmdbMovieService tmdbService,
                          MovieMapper movieMapper) {
        this.scraperService = scraperService;
        this.tmdbService = tmdbService;
        this.movieMapper = movieMapper;
    }

    @GetMapping("/movie-pipeline/console")
    public void testMoviePipelineConsole() {
        try {
            log.info("=== Starting Movie Pipeline Test ===");

            // Step 1: Scrape movies
            List<ScrapedMovieDto> scrapedMovies = scraperService.scrapeMovies();
            log.info("Found {} movies on cinema website", scrapedMovies.size());

            // Step 2: Process each movie
            for (ScrapedMovieDto scrapedMovie : scrapedMovies) {
                log.info("\nProcessing movie: {}", scrapedMovie.getTitle());

                try {
                    TmdbMoviePageDto tmdbResult = tmdbService.searchMovies(scrapedMovie.getTitle());

                    if (tmdbResult.getResults() != null && !tmdbResult.getResults().isEmpty()) {
                        TmdbMovieDto tmdbMovie = tmdbResult.getResults().getFirst();
                        log.info("√ TMDB Match found:");
                        log.info("  - Title: {}", tmdbMovie.getTitle());
                        log.info("  - Rating: {}", tmdbMovie.getVote_average());
                        log.info("  - Release Date: {}", tmdbMovie.getRelease_date());
                        log.info("  - Qualifies for Alert: {}", tmdbMovie.getVote_average() >= 7.0);
                    } else {
                        log.warn("× No TMDB match found");
                    }
                } catch (Exception e) {
                    log.error("× Error searching TMDB: {}", e.getMessage());
                }
            }

            log.info("\n=== Pipeline Test Complete ===");

        } catch (Exception e) {
            log.error("Pipeline test failed", e);
        }
    }


}
