package com.tmanea.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "tmdb")
@Data
public class TmdbConfig {
    private String apiKey = "APIKEYGOESHERE";
    private String baseUrl = "https://api.themoviedb.org/3";
}
