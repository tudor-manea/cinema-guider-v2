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
    public ResponseEntity<Page<Movie>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.debug("getAllMovies({}, {})", page, size);
        return ResponseEntity.ok(movieService.getAllMovies(PageRequest.of(page, size)));
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
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
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
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
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
    public void deleteAll() {
        movieService.deleteAll();
    }
}