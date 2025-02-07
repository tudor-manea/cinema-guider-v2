package com.tmanea.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmanea.backend.dto.LetterboxdMovieDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LetterboxdScraperService {
    private static final Logger logger = LoggerFactory.getLogger(LetterboxdScraperService.class);
    private static final String LETTERBOXD_BASE_URL = "https://letterboxd.com/film/";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public LetterboxdMovieDto tryUrls(String movieTitle, String releaseDate) throws IOException {
        LetterboxdMovieDto movieDto = scrapeMovieRating(movieTitle);
        String releaseYear = releaseDate.substring(0, 4);
        if (movieDto == null) {
            movieDto = scrapeMovieRating(movieTitle + " " + releaseYear);
        }
        return movieDto;
    }

    public LetterboxdMovieDto scrapeMovieRating(String movieTitle) throws IOException {
        String formattedTitle = formatTitleForUrl(movieTitle);
        String url = LETTERBOXD_BASE_URL + formattedTitle + "/";

        logger.info("Scraping Letterboxd rating for movie: {} (URL: {})", movieTitle, url);

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .timeout(10000)
                    .get();

            // Find JSON-LD script containing rating information
            Elements scriptElements = doc.select("script[type=application/ld+json]");
            if (!scriptElements.isEmpty()) {
                Element scriptElement = scriptElements.first();
                String jsonContent = scriptElement.html();

                // Clean the JSON content
                jsonContent = cleanJsonContent(jsonContent);

                // Parse JSON and extract rating
                try {
                    JsonNode jsonNode = objectMapper.readTree(jsonContent);
                    JsonNode aggregateRating = jsonNode.get("aggregateRating");

                    if (aggregateRating != null && aggregateRating.has("ratingValue")) {
                        float rating = (float) aggregateRating.get("ratingValue").asDouble();

                        LetterboxdMovieDto movieDto = new LetterboxdMovieDto();
                        movieDto.setTitle(movieTitle);
                        movieDto.setRating(rating);

                        logger.info("Successfully scraped rating for {}: {}", movieTitle, rating);
                        return movieDto;
                    }
                } catch (Exception e) {
                    logger.error("Error parsing JSON for movie {}: {}", movieTitle, e.getMessage());
                    return null;
                }
            }

            logger.warn("No rating found for movie {}", movieTitle);
            return null;

        } catch (IOException e) {
            logger.error("Error scraping Letterboxd rating for {}: {}", movieTitle, e.getMessage());
            throw e;
        }
    }

    /**
     * Cleans JSON content by removing HTML comments and other problematic characters.
     * This helps prevent parsing errors when processing the JSON-LD data.
     */
    private String cleanJsonContent(String jsonContent) {
        // Remove HTML comments
        jsonContent = jsonContent.replaceAll("/\\*.*?\\*/", "");
        jsonContent = jsonContent.replaceAll("<!--.*?-->", "");

        // Remove potential CDATA sections
        jsonContent = jsonContent.replaceAll("<!\\[CDATA\\[.*?\\]\\]>", "");

        // Remove leading/trailing whitespace and any stray forward slashes
        jsonContent = jsonContent.trim().replaceAll("^/+", "").replaceAll("/+$", "");

        // Clean up any multi-line comments that might be present
        jsonContent = jsonContent.replaceAll("(?m)^\\s*//.*$", "");

        return jsonContent;
    }

    private String formatTitleForUrl(String title) {
        if (title == null || title.isEmpty()) {
            return "";
        }

        String formatted = title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // Remove special characters
                .replaceAll("\\s+", "-")         // Replace spaces with hyphens
                .replaceAll("-+", "-")          // Remove multiple consecutive hyphens
                .replaceAll("^-|-$", "");       // Remove leading/trailing hyphens

        logger.debug("Formatted title '{}' to '{}'", title, formatted);
        return formatted;
    }
}