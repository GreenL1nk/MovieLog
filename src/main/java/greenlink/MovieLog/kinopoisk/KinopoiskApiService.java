package greenlink.MovieLog.kinopoisk;

import greenlink.MovieLog.movie.Movie;
import greenlink.MovieLog.movie.SearchedMovie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface KinopoiskApiService {

    Movie getMovieById(String endpoint);

    ArrayList<SearchedMovie> getMoviesByName(String endpoint, Map<String, List<String>> queryParams);

}
