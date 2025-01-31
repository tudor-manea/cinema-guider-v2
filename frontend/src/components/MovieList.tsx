import { useState, useEffect } from "react";
import { Movie } from '../types/movie';
import MovieCard from './MovieCard';
import '../styles/MovieList.css';

function MovieList() {
    const [movies, setMovies] = useState<Movie[]>([]);

    useEffect(() => {
        fetchMovies();
    }, []);

    const fetchMovies = () => {
        console.log('get movies');
        fetch('http://localhost:8080/movies')
            .then(response => response.json())
            .then(data => {
                setMovies(Array.isArray(data) ? data : data.content);
            })
            .catch(error => console.error('Error fetching movies', error));
    };

    const handleToggleSeen = (id: string) => {
        fetch(`http://localhost:8080/movies/${id}/seen`, {
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

    return (
        <div className="movies">
            {movies.map(movie => (
                <MovieCard 
                    key={movie.id} 
                    movie={movie} 
                    onToggleSeen={handleToggleSeen}
                />
            ))}
        </div>
    );
}

export default MovieList;