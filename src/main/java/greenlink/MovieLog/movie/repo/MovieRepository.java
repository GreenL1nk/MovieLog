package greenlink.MovieLog.movie.repo;

import greenlink.MovieLog.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenre(String genre);
}
