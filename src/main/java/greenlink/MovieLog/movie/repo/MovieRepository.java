package greenlink.MovieLog.movie.repo;

import greenlink.MovieLog.movie.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = """
            SELECT m.* 
            FROM movies m 
            WHERE m.name_ru ILIKE :search COLLATE russian_nocase
            OR m.name_en ILIKE :search
            """, nativeQuery = true)
    Page<Movie> findMoviesByName(@Param("search") String search, Pageable pageable);

    List<Movie> findByGenre(String genre);
}
