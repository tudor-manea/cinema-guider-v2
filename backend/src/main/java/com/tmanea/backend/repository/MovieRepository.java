package com.tmanea.backend.repository;

import com.tmanea.backend.domain.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findTop10ByOrderByPopularityDesc();
    List<Movie> findAllByOrderByPopularityDesc();
    
    @Query("SELECT m FROM Movie m ORDER BY m.vote_average DESC")
    List<Movie> findAllByOrderByVoteAverageDesc();
    
    // For seen movies
    @Query("SELECT m FROM Movie m WHERE m.seen = true ORDER BY m.vote_average DESC")
    List<Movie> findSeenMoviesByVoteAverageDesc();
    
    @Query("SELECT m FROM Movie m WHERE m.seen = true ORDER BY m.popularity DESC")
    List<Movie> findSeenMoviesByPopularityDesc();
}