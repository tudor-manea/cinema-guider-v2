package com.tmanea.backend.repository;

import com.tmanea.backend.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByReleaseDateStartsWith(String year);
    List<Movie> findByRatingGreaterThanEqual(Double rating);
    List<Movie> findTop10ByOrderByPopularityDesc();
}