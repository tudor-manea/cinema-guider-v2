import { useState, useEffect } from "react";
import { Movie } from '../types/movie';

function MovieList() {
    const[movies, setMovies] = useState<Movie[]>([]);

    useEffect(() => {
        fetch('http://localhost:8080/movies')
            .then(response => response.json())
            .then(data => {
                setMovies(Array.isArray(data) ? data : data.content);
            })
            .catch(error => console.error('Error fetching movies', error));
    }, []);

    return (
        <div>
            <h1>Movies</h1>
            <pre>{JSON.stringify(movies, null, 2)}</pre>
        </div>
    );
}

export default MovieList;