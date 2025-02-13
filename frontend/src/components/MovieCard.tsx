import { Star, ChevronDown, ChevronUp } from "lucide-react";
import { Movie } from '../types/movie';
import { useState } from 'react';

interface MovieCardProps {
    movie: Movie;
    onToggleSeen: (id: string) => void;
}

const MovieCard = ({ movie, onToggleSeen }: MovieCardProps) => {
    const [isExpanded, setIsExpanded] = useState(false);

    const handleToggleSeen = () => {
        onToggleSeen(movie.id);
    };

    const handleToggleExpand = () => {
        setIsExpanded(!isExpanded);
    };

    return (
        // Outer container for glow effect
        <div className="w-full max-w-4xl p-1 rounded-2xl bg-gradient-to-r from-blue-500/20 via-purple-500/20 to-pink-500/20">
            {/* Main card container */}
            <div className="bg-[#2a2557] rounded-xl p-4 md:p-6 flex flex-col md:flex-row gap-4 md:gap-6">
                {/* Movie Poster */}
                <div className="w-32 md:w-48 mx-auto md:mx-0 flex-shrink-0">
                    <img 
                        src={`https://image.tmdb.org/t/p/w500${movie.poster_path}`}
                        alt={movie.title}
                        className="w-full h-auto rounded-lg object-cover shadow-lg"
                    />
                </div>

                {/* Content Container */}
                <div className="flex-1 flex flex-col">
                    {/* Title and Rating */}
                    <div className="flex justify-between items-start gap-2 mb-3 md:mb-4">
                        <a 
    href="https://www.odeoncinemas.ie/cinemas/point-square/"
    target="_blank"
    rel="noopener noreferrer"
    className="text-2xl md:text-3xl font-semibold text-white flex-1 hover:text-blue-400 transition-colors cursor-pointer"
>
    {movie.title}
</a>
                        <div className="flex items-center gap-1 md:gap-2 bg-black/50 rounded-xl px-2 md:px-3 py-1 md:py-2 flex-shrink-0">
                            <Star className="w-5 h-5 md:w-6 md:h-6 text-yellow-400 fill-yellow-400" />
                            <span className="text-lg md:text-xl font-bold text-white">
                                {movie.vote_average.toFixed(1)}
                            </span>
                        </div>
                    </div>

                    {/* Overview */}
                    <div className="relative">
                        <p className={`text-gray-200 text-base md:text-lg leading-relaxed mb-2 md:mb-4 ${!isExpanded ? 'line-clamp-3 md:line-clamp-none' : ''}`}>
                            {movie.overview}
                        </p>
                        <button 
                            onClick={handleToggleExpand}
                            className="md:hidden flex items-center gap-1 text-blue-400 hover:text-blue-300 transition-colors"
                        >
                            <span>{isExpanded ? 'Show Less' : 'Show More'}</span>
                            {isExpanded ? <ChevronUp className="w-4 h-4" /> : <ChevronDown className="w-4 h-4" />}
                        </button>
                    </div>

                    {/* Seen Checkbox */}
                    <div className="mt-auto pt-2">
                        <label className="flex items-center gap-2 md:gap-3 w-fit px-3 md:px-4 py-1.5 md:py-2 bg-black/30 hover:bg-black/40 transition-colors rounded-full cursor-pointer">
                            <input
                                type="checkbox"
                                checked={movie.seen}
                                onChange={handleToggleSeen}
                                className="w-4 h-4 md:w-5 md:h-5 rounded-sm"
                            />
                            <span className="text-white font-medium text-base md:text-lg">Seen</span>
                        </label>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MovieCard;