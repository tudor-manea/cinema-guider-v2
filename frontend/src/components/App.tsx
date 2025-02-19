import MovieList from "./MovieList";
import '../styles/App.css';
import { Button } from "@/components/ui/button"
import { ListChecks, SlidersHorizontal } from "lucide-react"
import { useState } from "react";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
} from "@/components/ui/select"
import { BackgroundBeams } from "@/components/ui/background-beams"

function App() {
    const [showSeen, setShowSeen] = useState(false);
    const [sortBy, setSortBy] = useState("best");

    const toggleView = () => {
        setShowSeen(!showSeen);
    };

    return (
        <div className="relative w-full min-h-screen overflow-hidden">
            <BackgroundBeams className="fixed inset-0" />
            <div className="app-container relative z-10">
                <h1 className="app-title">Cinema Guider</h1>
                <p className="app-description">
                    Tracking the best movies in Odeon Point Square
                </p>
                <div className="button-container">
                    <Button
                        variant="outline"
                        size="lg"
                        className="action-button w-[160px]"
                        onClick={toggleView}
                    >
                        <ListChecks className="w-5 h-5" />
                        {showSeen ? 'Current Movies' : 'Seen Movies'}
                    </Button>
                    <Select value={sortBy} onValueChange={setSortBy}>
                        <SelectTrigger className="action-button w-[160px] h-10 px-4">
                            <SlidersHorizontal className="w-5 h-5" />
                            Sort
                        </SelectTrigger>
                        <SelectContent className="sort-content">
                            <SelectItem value="best">Best Rated</SelectItem>
                            <SelectItem value="popular">Most Popular</SelectItem>
                        </SelectContent>
                    </Select>
                </div>
                <MovieList showSeen={showSeen} sortBy={sortBy} />
            </div>
        </div>
    );
}

export default App;