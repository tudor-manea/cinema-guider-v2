import MovieList from "./MovieList";
import '../styles/App.css';
import { Button } from "@/components/ui/button"
import { ListChecks, SlidersHorizontal } from "lucide-react"

function App() {
    return (
        <div className="app-container">
            <h1 className="app-title">Cinema Guider</h1>
            <p className="app-description">
                Discover and track the best movies currently showing in Dublin cinemas
            </p>
            <div className="button-container">
                <Button variant="outline" size="lg" className="action-button">
                    <ListChecks className="w-5 h-5" />
                    Seen Movies
                </Button>
                <Button variant="outline" size="lg" className="action-button">
                    <SlidersHorizontal className="w-5 h-5" />
                    Sort
                </Button>
            </div>
            <MovieList />
        </div>
    );
}

export default App;