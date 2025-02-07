import { Movie } from '../types/movie';
import '../styles/MovieCard.css';
import { Star } from "lucide-react";

interface MovieCardProps {
    movie: Movie;
    onToggleSeen: (id: string) => void;
}

function MovieCard({ movie, onToggleSeen }: MovieCardProps) {
    const handleToggleSeen = () => {
        onToggleSeen(movie.id);
    };

    return (
        <div className="movie-card">
            <div className="movie-poster">
                <img src={`https://image.tmdb.org/t/p/w500${movie.poster_path}`} alt={movie.title} />
            </div>
            <div className="movie-info">
                <div className="movie-header">
                    <h2 className="movie-title">{movie.title}</h2>
                    <div className="score-star">
                        <Star className="star-icon" size={24} />
                        <span>{movie.vote_average.toFixed(1)}</span>
                    </div>
                </div>
                <p className="movie-overview">{movie.overview}</p>
                <label className="seen-checkbox">
                    <input
                        type="checkbox"
                        checked={movie.seen}
                        onChange={handleToggleSeen}
                    />
                    <span className="seen-text">Seen</span>
                </label>
            </div>
        </div>
    );
}

export default MovieCard;