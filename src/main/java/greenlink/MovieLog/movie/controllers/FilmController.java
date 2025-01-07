package greenlink.MovieLog.movie.controllers;

import greenlink.MovieLog.kinopoisk.KinopoiskApiServiceImpl;
import greenlink.MovieLog.movie.Movie;
import greenlink.MovieLog.movie.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/film")
public class FilmController implements BaseMovieController {

    private final MovieService movieService;
    private final KinopoiskApiServiceImpl kinopoiskApiServiceImpl;

    public FilmController(MovieService movieService, KinopoiskApiServiceImpl kinopoiskApiServiceImpl) {
        this.movieService = movieService;
        this.kinopoiskApiServiceImpl = kinopoiskApiServiceImpl;
    }

    @Override
    public List<Movie> getAll() {
        return List.of();
    }

    @Override
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {

        Movie movieById = movieService.getMovieById(id);
        if (movieById == null) {
            movieById = kinopoiskApiServiceImpl.getMovieById("/v2.2/films/" + id);
            movieService.save(movieById);
        }

        model.addAttribute("movie", movieById);

        return "movie-details";
    }
}
