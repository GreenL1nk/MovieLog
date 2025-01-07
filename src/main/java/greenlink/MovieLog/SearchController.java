package greenlink.MovieLog;

import greenlink.MovieLog.kinopoisk.KinopoiskApiServiceImpl;
import greenlink.MovieLog.movie.SearchedMovie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class SearchController {

    KinopoiskApiServiceImpl kinopoiskApiService;

    public SearchController(KinopoiskApiServiceImpl kinopoiskApiService) {
        this.kinopoiskApiService = kinopoiskApiService;
    }

    @GetMapping("/search")
    public ResponseEntity<ArrayList<SearchedMovie>> search(@RequestParam String query, @RequestParam(defaultValue = "1") int page) {
        String endpoint = "/v2.1/films/search-by-keyword";

        Map<String, List<String>> queryParams = new HashMap<>();
        queryParams.put("keyword", Collections.singletonList(query));
        queryParams.put("page", Collections.singletonList(String.valueOf(page)));

        ArrayList<SearchedMovie> moviesByName = kinopoiskApiService.getMoviesByName(endpoint, queryParams);
        if (moviesByName.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(moviesByName);
    }
}