import { Movie } from '../types/movie';
import '../styles/MovieCard.css';

interface MovieCardProps {
    movie: Movie;
}

function MovieCard({ movie }: MovieCardProps) {
    return (
        <div className="movie-card">
            <div className="movie-poster">
                <img src={`https://image.tmdb.org/t/p/w500${movie.poster_path}`} alt={movie.title} />
            </div>
            <div className="movie-info">
                <h2 className="movie-title">{movie.title}</h2>
                <div className="score-circle">
                    <span>{movie.vote_average.toFixed(1)}</span>
                </div>
            </div>
        </div>
    );
}

export default MovieCard;