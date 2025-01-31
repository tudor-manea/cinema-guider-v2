import MovieList from "./MovieList";
import '../styles/App.css';

function App() {
    return (
        <div className="app-container">
            <h1 className="app-title">Cinema Guider</h1>
            <MovieList />
        </div>
    );
}

export default App;