package greenlink.MovieLog;

import greenlink.MovieLog.kinopoisk.KinopoiskApiServiceImpl;
import greenlink.MovieLog.movie.Movie;
import greenlink.MovieLog.movie.MovieService;
import greenlink.MovieLog.movie.SearchedMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final KinopoiskApiServiceImpl kinopoiskApiService;
    private final MovieService movieService;

    public SearchController(KinopoiskApiServiceImpl kinopoiskApiService, MovieService movieService) {
        this.kinopoiskApiService = kinopoiskApiService;
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public ResponseEntity<ArrayList<SearchedMovie>> search(@RequestParam String query, @RequestParam(defaultValue = "1") int page) {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<Movie> moviesPage = movieService.getMoviesByName("%" + query + "%", pageable);

        ArrayList<SearchedMovie> resultMovies = new ArrayList<>();

        for (Movie movie : moviesPage.getContent()) {
            SearchedMovie searchedMovie = new SearchedMovie();
            searchedMovie.setFilmId(movie.getKinopoiskId());
            searchedMovie.setNameRu(movie.getNameRu());
            searchedMovie.setRating(movie.getRatingKinopoisk());
            searchedMovie.setYear(movie.getYear());
            searchedMovie.setPosterUrlPreview(movie.getPosterUrlPreview());
            resultMovies.add(searchedMovie);
        }

        if (resultMovies.size() < pageSize) {
            String endpoint = "/v2.1/films/search-by-keyword";
            Map<String, List<String>> queryParams = new HashMap<>();
            queryParams.put("keyword", Collections.singletonList(query));
            queryParams.put("page", Collections.singletonList(String.valueOf(page)));

            List<SearchedMovie> apiMovies = kinopoiskApiService.getMoviesByName(endpoint, queryParams).stream().limit(10).toList();

            apiMovies.forEach(movie -> {
                Movie movieById = kinopoiskApiService.getMovieById(movie.getFilmId().toString());
                movieService.saveMoviesAsync(movieById);
            });

            int remaining = pageSize - resultMovies.size();
            for (int i = 0; i < remaining && i < apiMovies.size(); i++) {
                resultMovies.add(apiMovies.get(i));
            }
        }

        if (resultMovies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultMovies);
    }
}
