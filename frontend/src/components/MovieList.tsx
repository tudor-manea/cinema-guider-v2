import { useState, useEffect } from "react";
import { Movie } from '../types/movie';
import MovieCard from './MovieCard';
import '../styles/MovieList.css';

interface MovieListProps {
    showSeen: boolean;
}

function MovieList({ showSeen }: MovieListProps) {
    const [movies, setMovies] = useState<Movie[]>([]);

    useEffect(() => {
        fetchMovies();
    }, []);

    const fetchMovies = () => {
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