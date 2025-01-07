package greenlink.MovieLog.movie.controllers;

import greenlink.MovieLog.movie.Movie;
import greenlink.MovieLog.movie.MovieService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController implements BaseMovieController {

    private final MovieService movieService;

    public SeriesController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public List<Movie> getAll() {
        return List.of();
    }

    @Override
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {

        movieService.getMovieById(id);

        return null;
    }
}