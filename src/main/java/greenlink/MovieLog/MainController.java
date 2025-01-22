package greenlink.MovieLog;

import greenlink.MovieLog.kinopoisk.KinopoiskApiServiceImpl;
import greenlink.MovieLog.movie.SearchedMovie;
import greenlink.MovieLog.top.TopCollection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/")
public class MainController {

    private final KinopoiskApiServiceImpl kinopoiskApiServiceImpl;
    private final String endpoint = "/v2.2/films/collections";

    public MainController(KinopoiskApiServiceImpl kinopoiskApiServiceImpl) {
        this.kinopoiskApiServiceImpl = kinopoiskApiServiceImpl;
    }

    @ModelAttribute("topcollections")
    public void getTopCollections(Model model) {
        HashMap<TopCollection.TopType, List<SearchedMovie> > moviesByTop = new HashMap<>();
        for (TopCollection.TopType value : TopCollection.TopType.values()) {
            Map<String, List<String>> queryParams = new HashMap<>();
            queryParams.put("type", Collections.singletonList(value.name()));
            queryParams.put("page", Collections.singletonList(String.valueOf(1)));

            moviesByTop.put(value, kinopoiskApiServiceImpl.getMoviesByTop(endpoint, queryParams));
        }
        model.addAttribute("topcollections", moviesByTop);
    }

    @GetMapping
    public String showTopCollections() {
        return "index";
    }

}