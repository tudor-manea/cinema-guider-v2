package com.tmanea.backend.service;

import com.tmanea.backend.domain.Movie;
import com.tmanea.backend.dto.ScrapedMovieDto;
import com.tmanea.backend.repository.MovieRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieScraperService {
    private static final String ODEON_URL = "https://entertainment.ie/cinema/cinema-listings/dublin/odeon-point-square/";
    private final MovieRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(MovieScraperService.class);

    public MovieScraperService(MovieRepository repository) {
        this.repository = repository;
    }

    private boolean isNewMovie(ScrapedMovieDto scrapedMovie) {
        List<Movie> existingMovies = repository.findByTitleContainingIgnoreCase(scrapedMovie.getTitle());
        return existingMovies.isEmpty();
    }

    public List<ScrapedMovieDto> findNewMovies() throws IOException {
        List<ScrapedMovieDto> allScrapedMovies = scrapeMovies();
        List<ScrapedMovieDto> newMovies = allScrapedMovies.stream()
                .filter(this::isNewMovie)
                .collect(Collectors.toList());

        logger.info("Found {} new movies out of {} total scraped movies",
                newMovies.size(), allScrapedMovies.size());

        return newMovies;
    }

    public List<ScrapedMovieDto> scrapeMovies() throws IOException {
        logger.info("Starting movie scraping from {}", ODEON_URL);
        List<ScrapedMovieDto> scrapedMovies = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(ODEON_URL)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .timeout(10000)
                    .get();

            // Select all movie blocks instead of just movie headers
            Elements movieBlocks = doc.select(".movie-times-block");
            logger.debug("Found {} movie blocks on page", movieBlocks.size());

            for (Element movieBlock : movieBlocks) {
                // Look for the title in the block-content section
                Element titleElement = movieBlock.select(".block-content h3").first();

                if (titleElement != null) {
                    String title = titleElement.ownText().trim(); // Using ownText() to avoid getting rating text

                    if (!title.isEmpty()) {
                        ScrapedMovieDto movieDto = new ScrapedMovieDto();
                        movieDto.setTitle(title);

                        // Get the URL if there's a link
                        Element linkElement = movieBlock.select(".block-content a").first();
                        if (linkElement != null) {
                            movieDto.setUrl(linkElement.attr("href"));
                        }

                        scrapedMovies.add(movieDto);
                        logger.debug("Scraped movie: {}", title);
                    }
                }
            }

            logger.info("Successfully scraped {} movies", scrapedMovies.size());
        } catch (IOException e) {
            logger.error("Error scraping movies: {}", e.getMessage(), e);
            throw e;
        }

        return scrapedMovies;
    }


}
