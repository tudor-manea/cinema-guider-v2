package com.tmanea.backend.service;

import com.tmanea.backend.domain.Movie;
import com.tmanea.backend.dto.ScrapedMovieDto;
import com.tmanea.backend.dto.TmdbMovieDto;
import com.tmanea.backend.mapper.MovieMapper;
import com.tmanea.backend.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class ActiveMovieService {
    private final MovieScraperService scraperService;
    private final MovieRepository repository;
    private final MovieMapper mapper;


    @Autowired
    public ActiveMovieService(MovieScraperService scraperService, MovieRepository repository, MovieMapper mapper) {
        this.scraperService = scraperService;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void scheduledActiveMovieSync() {
        try {
            log.info("Starting scheduled active movie sync at midnight");
            getActiveMovies();
            log.info("Completed scheduled active movie sync");
        } catch (IOException e) {
            log.error("Error during scheduled active movie sync: {}", e.getMessage(), e);
        }
    }


    public void getActiveMovies() throws IOException {
        log.info("=== STARTING ACTIVE MOVIE SYNC ===");

        List<Movie> ogMovieList = repository.findAll();
        List<ScrapedMovieDto> scrapedMoviesDtoList = scraperService.scrapeMovies();
        List<Movie> scrapedMovies = new ArrayList<>();

        for (ScrapedMovieDto scrapedMovie : scrapedMoviesDtoList) {
            TmdbMovieDto tmdbMovie = mapper.scrapedToTmdbMovie(scrapedMovie);
            if (tmdbMovie != null) { // Add null check here
                Movie movie = mapper.tmdbToMovie(tmdbMovie);
                if (movie != null) {
                    log.info("MOVIE SCRAPED WITH TITLE: {}", movie.getTitle());
                    scrapedMovies.add(movie);

                    boolean movieExists = ogMovieList.stream()
                            .anyMatch(existing -> existing.getTitle().equalsIgnoreCase(movie.getTitle()));

                    if (!movieExists && movie.getVote_average() != 0) {
                        repository.save(movie);
                        log.info("NEW MOVIE SAVED TO DB WITH TITLE {}", movie.getTitle());
                    }
                }
            } else {
                log.warn("Could not find TMDB data for scraped movie: {}", scrapedMovie.getTitle());
            }
        }

        // for movie in og db list, if movie not in scraped list, delete it from db
        // add logging whenever movie added/deleted, maybe print all the movies at the end
        for (Movie movie : ogMovieList) {
            boolean stillActive = scrapedMovies.stream()
                    .anyMatch(scraped -> scraped.getTitle().equalsIgnoreCase(movie.getTitle()));

            if (!stillActive && !movie.isSeen()) {
                repository.deleteById(movie.getId());
                log.info("MOVIE DELETED FROM DB WITH TITLE {}", movie.getTitle());
            }
        }
    }
}
