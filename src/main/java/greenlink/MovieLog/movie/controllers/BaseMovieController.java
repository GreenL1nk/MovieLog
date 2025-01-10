package greenlink.MovieLog.movie.controllers;

import greenlink.MovieLog.movie.Movie;
import org.springframework.ui.Model;

import java.util.List;

public interface BaseMovieController {
    List<Movie> getAll();

    String getById(Long id, Model model);
}
