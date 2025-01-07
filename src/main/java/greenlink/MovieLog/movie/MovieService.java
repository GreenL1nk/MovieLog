package greenlink.MovieLog.movie;

import greenlink.MovieLog.movie.repo.MovieRepository;
import jakarta.annotation.Nullable;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Nullable
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                              .orElse(null);
    }

    public void save(Movie movie) {
        movieRepository.saveAndFlush(movie);
        log.info("Save movie: " + movie.getNameRu() + " (" + movie.getKinopoiskId() + ")");
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }
}