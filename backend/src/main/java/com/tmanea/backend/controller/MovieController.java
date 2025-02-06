// MovieController.java
package com.tmanea.backend.controller;

import com.tmanea.backend.domain.Movie;
import com.tmanea.backend.service.ActiveMovieService;
import com.tmanea.backend.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j // Lombok annotation to generate logger field "log"
@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "*") // Configure appropriately for your setup
public class MovieController {

    private final MovieService movieService;
    private final ActiveMovieService activeService;

    @Autowired
    public MovieController(MovieService movieService, ActiveMovieService activeService) {
        this.movieService = movieService;
        this.activeService = activeService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String sort) {
        List<Movie> movies;
        if (sort != null && sort.equals("popularity")) {
            movies = movieService.getAllMoviesByPopularity();
        } else {
            // Default sorting by vote average
            movies = movieService.getAllMoviesByVoteAverage();
        }
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        log.debug("createMovie({})", movie);
        return ResponseEntity.ok(movieService.saveMovie(movie));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Movie>> createMovies(@RequestBody List<Movie> movies) {
        log.debug("createMovies({})", movies);
        return ResponseEntity.ok(movieService.saveMovies(movies));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String id) {
        log.debug("getMovie({})", id);
        return movieService.getMovie(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String title) {
        log.debug("searchMovies({})", title);
        return ResponseEntity.ok(movieService.searchMovies(title));
    }

//    @GetMapping("/year/{year}")
//    public ResponseEntity<List<Movie>> getMoviesByYear(@PathVariable String year) {
//        log.debug("getMoviesByYear({})", year);
//        return ResponseEntity.ok(movieService.getMoviesByYear(year));
//    }
//
//    @GetMapping("/rating/{rating}")
//    public ResponseEntity<List<Movie>> getMoviesByMinRating(@PathVariable Double rating) {
//        log.debug("getMoviesByMinRating({})", rating);
//        return ResponseEntity.ok(movieService.getMoviesByMinRating(rating));
//    }

    @GetMapping("/popular")
    public ResponseEntity<List<Movie>> getTopPopularMovies() {
        log.debug("getTopPopularMovies()");
        return ResponseEntity.ok(movieService.getTopPopularMovies());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        log.debug("deleteMovie({})", id);
        if (!movieService.exists(id)) {
            return ResponseEntity.notFound().build();
        }
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activeSync")
    public void getActiveMovies() throws IOException {
        log.debug("getActiveMovies()");
        activeService.getActiveMovies();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        movieService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/seen")
    public ResponseEntity<Movie> toggleSeen(@PathVariable String id) {
        try {
            return ResponseEntity.ok(movieService.toggleSeen(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/seen")
    public ResponseEntity<List<Movie>> getSeenMovies(@RequestParam(required = false) String sort) {
        List<Movie> movies;
        if (sort != null && sort.equals("popularity")) {
            movies = movieService.getSeenMoviesByPopularity();
        } else {
            // Default sorting by vote average
            movies = movieService.getSeenMoviesByVoteAverage();
        }
        return ResponseEntity.ok(movies);
    }
}