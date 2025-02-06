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

    public Optional<Movie> getMovie(String id) {
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

    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        movieRepository.deleteAllInBatch();
    }

    public Movie toggleSeen(String id) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setSeen(!movie.isSeen());
        return movieRepository.save(movie);
    }

    public boolean exists(String id) {
        return movieRepository.existsById(id);
    }

    public List<Movie> getActiveMovies() {
        return movieRepository.findAllByOrderByPopularityDesc();
    }

    public List<Movie> getAllMoviesByVoteAverage() {
        return movieRepository.findAllByOrderByVoteAverageDesc();
    }

    public List<Movie> getAllMoviesByPopularity() {
        return movieRepository.findAllByOrderByPopularityDesc();
    }

    public List<Movie> getSeenMoviesByVoteAverage() {
        return movieRepository.findSeenMoviesByVoteAverageDesc();
    }

    public List<Movie> getSeenMoviesByPopularity() {
        return movieRepository.findSeenMoviesByPopularityDesc();
    }
}
