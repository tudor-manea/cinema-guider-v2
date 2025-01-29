package com.tmanea.backend.controller;

import com.tmanea.backend.dto.TmdbMoviePageDto;
import com.tmanea.backend.service.TmdbMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/tmdb/movies")
public class TmdbMovieController {
    private static final Logger logger = LoggerFactory.getLogger(TmdbMovieController.class);

    private final TmdbMovieService tmdbService;

    @Autowired
    public TmdbMovieController(TmdbMovieService tmdbService) {this.tmdbService = tmdbService;}


    // TODO: Change this so that it pulls the first x many pages of popular movies
//    @GetMapping("/popular")
//    public TmdbMoviePageDto getPopularMovies(@RequestParam(defaultValue = "1") int page) {
//        logger.info("/tmdb/movies/popular -> getPopularMovies()");
//        return tmdbService.getPopularMovies(page);
//    }

    @GetMapping("/search")
    public TmdbMoviePageDto searchMovies(@RequestParam String query,
                                         @RequestParam(defaultValue = "1") int page) {
        logger.info("/search -> searchMovies()");
        return tmdbService.searchMovies(query);
    }
}
