package com.tmanea.backend.service;

import com.tmanea.backend.config.TmdbConfig;
import com.tmanea.backend.dto.TmdbMoviePageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TmdbMovieService {
    private static final Logger logger = LoggerFactory.getLogger(TmdbMovieService.class);

    private final RestTemplate restTemplate;
    private final TmdbConfig tmdbConfig;

    @Autowired
    public TmdbMovieService(TmdbConfig tmdbProperties) {
        this.restTemplate = new RestTemplate();
        this.tmdbConfig = tmdbProperties;
    }

    public TmdbMoviePageDto getPopularMovies(int page) {
        String url = UriComponentsBuilder
                .fromHttpUrl(tmdbConfig.getBaseUrl())
                .path("movie/popular")
                .queryParam("api_key", tmdbConfig.getApiKey())
                .queryParam("page", page)
                .build()
                .toUriString();

        logger.info("GET request to: {}", url);

        return restTemplate.getForObject(url, TmdbMoviePageDto.class);
    }

    public TmdbMoviePageDto searchMovies(String query, int page) {
        String url = UriComponentsBuilder
                .fromHttpUrl(tmdbConfig.getBaseUrl())
                .path("/search/movie")
                .queryParam("api_key", tmdbConfig.getApiKey())
                .queryParam("query", query)
                .queryParam("page", page)
                .build()
                .toUriString();

        return restTemplate.getForObject(url, TmdbMoviePageDto.class);
    }
}
