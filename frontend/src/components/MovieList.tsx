import { useState, useEffect } from "react";
import { Movie } from '../types/movie';
import MovieCard from './MovieCard';
import '../styles/MovieList.css';

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
        <div className="movies">
            {movies.map(movie => (
                <MovieCard key={movie.id} movie={movie} />
            ))}
        </div>
    );
}

export default MovieList;