import { useState, useEffect } from "react";
import { Movie } from '../types/movie';
import MovieCard from './MovieCard';
import '../styles/MovieList.css';

const baseUrl = "https://test.1ag.eu/api";

interface MovieListProps {
    showSeen: boolean;
    sortBy: string;
}

function MovieList({ showSeen, sortBy }: MovieListProps) {
    const [movies, setMovies] = useState<Movie[]>([]);

    useEffect(() => {
        fetchMovies();
    }, [showSeen, sortBy]);

    // TODO: Make paths relative

    const fetchMovies = () => {
        // If we are showing seen movies, hit the /movies/seen endpoint
        let url = showSeen
            ? baseUrl + '/movies/seen'
            : baseUrl + '/movies'

        // For "popular" sorting we add the query parameter; best is the default
        if (sortBy === "popular") {
            url += '?sort=popularity';
        }

        fetch(url)
            .then(response => response.json())
            .then(data => {
                setMovies(Array.isArray(data) ? data : data.content);
            })
            .catch(error => console.error('Error fetching movies', error));
    };

    const handleToggleSeen = (id: string) => {
        fetch(baseUrl + `/movies/${id}/seen`, {
            method: 'PUT',
        })
            .then(response => response.json())
            .then(updatedMovie => {
                setMovies(movies.map(movie =>
                    movie.id === id ? updatedMovie : movie
                ));
            })
            .catch(error => console.error('Error toggling seen status', error));
    };

    const filteredMovies = movies.filter(movie => movie.seen === showSeen);

    return (
        <div className="movies-container">
            <h2 className="view-title">{showSeen ? 'Seen Movies' : 'Current Movies'}</h2>
            <div className="movies">
                {filteredMovies.map(movie => (
                    <MovieCard
                        key={movie.id}
                        movie={movie}
                        onToggleSeen={handleToggleSeen}
                    />
                ))}
                {filteredMovies.length === 0 && (
                    <p className="no-movies">
                        {showSeen
                            ? "No movies marked as seen yet :("
                            : "No current movies to show"}
                    </p>
                )}
            </div>
        </div>
    );
}

export default MovieList;