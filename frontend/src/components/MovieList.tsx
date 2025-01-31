import { useState, useEffect } from "react";
import { Movie } from '../types/movie';
import './MovieList.css';

function MovieList() {
    const [movies, setMovies] = useState<Movie[]>([]);

    useEffect(() => {
        fetch('http://localhost:8080/movies')
            .then(response => response.json())
            .then(data => {
                setMovies(Array.isArray(data) ? data : data.content);
            })
            .catch(error => console.error('Error fetching movies', error));
    }, []);

    return (
        <div className="movie-list-container">
            <h1>Movies</h1>
            <div className="movies">
                {movies.map(movie => (
                    <div key={movie.id} className="movie-item">
                        <img src={`https://image.tmdb.org/t/p/w185${movie.poster_path}`}  alt={movie.title} className="movie-poster" />
                        <div className="movie-details">
                            <strong>{movie.title}</strong>
                            <p>Score: {movie.vote_average}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default MovieList;