package com.tmanea.backend.service;

import com.tmanea.backend.dto.TmdbMoviePageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class TmdbMovieService {
    private static final Logger logger = LoggerFactory.getLogger(TmdbMovieService.class);

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final WebClient webClient;


    // todo: add apikey to application properties
    @Autowired
    public TmdbMovieService(@Value("${tmdb.api.key}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.webClient = WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3").build();
        this.apiKey = apiKey;
    }

    // this will return all the movies with title = query
    public TmdbMoviePageDto searchMovies(String query) {
        Mono<TmdbMoviePageDto> tmdbResultPage = this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", query)
                        .queryParam("include_adult", false)
                        .queryParam("include_video", false)
                        .build())
                .header("Authorization", "Bearer " + this.apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TmdbMoviePageDto.class);

        TmdbMoviePageDto tmdbResponsePage = tmdbResultPage.block();
        if (tmdbResponsePage == null || tmdbResponsePage.getResults() == null) {
            logger.error("No movie with title {} found", query);
        }
        return tmdbResponsePage;
    }
}