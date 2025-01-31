package com.tmanea.backend.service;

import com.tmanea.backend.domain.Movie;
import com.tmanea.backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional // ensure consistent database state
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> saveMovies(List<Movie> movies) {
        return (List<Movie>) movieRepository.saveAll(movies);
    }

    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Optional<Movie> getMovie(Long id) {
        return movieRepository.findById(id);
    }


    public List<Movie> searchMovies(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

//    public List<Movie> getMoviesByYear(String year) {
//        return movieRepository.findByReleaseDateStartsWith(year);
//    }
//
//    public List<Movie> getMoviesByMinRating(Double rating) {
//        return movieRepository.findByRatingGreaterThanEqual(rating);
//    }

    public List<Movie> getTopPopularMovies() {
        return movieRepository.findTop10ByOrderByPopularityDesc();
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public boolean exists(Long id) {
        return movieRepository.existsById(id);
    }

    public List<Movie> getActiveMovies() {
        return movieRepository.findAllByOrderByPopularityDesc();
    }

}
