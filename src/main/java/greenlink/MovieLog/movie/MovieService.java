package greenlink.MovieLog.movie;

import greenlink.MovieLog.movie.repo.MovieRepository;
import jakarta.annotation.Nullable;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Log
public class MovieService {

    private final MovieRepository movieRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

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

    public Page<Movie> getMoviesByName(String search, Pageable pageable) {
        return movieRepository.findMoviesByName(search, pageable);
    }

    public void saveMoviesAsync(Movie... movies) {
        for (Movie movie : movies) {
            executorService.submit(() -> {
                movieRepository.save(movie);
                log.info("Save movie: " + movie.getNameRu() + " (" + movie.getKinopoiskId() + ")");
            });
        }
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }
}