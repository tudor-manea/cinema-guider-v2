import React from 'react';
import { Star } from "lucide-react";
import { Movie } from '../types/movie';

interface MovieCardProps {
    movie: Movie;
    onToggleSeen: (id: string) => void;
}

const MovieCard = ({ movie, onToggleSeen }: MovieCardProps) => {
    const handleToggleSeen = () => {
        onToggleSeen(movie.id);
    };

    return (
        // Outer container for glow effect
        <div className="w-full max-w-4xl p-1 rounded-2xl bg-gradient-to-r from-blue-500/20 via-purple-500/20 to-pink-500/20">
            {/* Main card container */}
            <div className="bg-[#2a2557] rounded-xl p-6 flex flex-col md:flex-row gap-6">
                {/* Movie Poster */}
                <div className="w-full md:w-48 flex-shrink-0">
                    <img 
                        src={`https://image.tmdb.org/t/p/w500${movie.poster_path}`}
                        alt={movie.title}
                        className="w-full h-auto rounded-lg object-cover"
                    />
                </div>

                {/* Content Container */}
                <div className="flex-1 flex flex-col">
                    {/* Title and Rating */}
                    <div className="flex justify-between items-start gap-4 mb-4">
                        <h2 className="text-3xl font-semibold text-white flex-1">{movie.title}</h2>
                        <div className="flex items-center gap-2 bg-black/50 rounded-xl px-3 py-2">
                            <Star className="w-6 h-6 text-yellow-400 fill-yellow-400" />
                            <span className="text-xl font-bold text-white">
                                {movie.vote_average.toFixed(1)}
                            </span>
                        </div>
                    </div>

                    {/* Overview */}
                    <p className="text-gray-200 text-lg leading-relaxed mb-8">
                        {movie.overview}
                    </p>

                    {/* Seen Checkbox */}
                    <div className="mt-auto">
                        <label className="flex items-center gap-3 w-fit px-4 py-2 bg-black/30 hover:bg-black/40 transition-colors rounded-full cursor-pointer">
                            <input
                                type="checkbox"
                                checked={movie.seen}
                                onChange={handleToggleSeen}
                                className="w-5 h-5 rounded-sm"
                            />
                            <span className="text-white font-medium text-lg">Seen</span>
                        </label>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MovieCard;