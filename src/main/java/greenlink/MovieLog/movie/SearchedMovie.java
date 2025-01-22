package greenlink.MovieLog.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchedMovie {

    Long filmId;
    Long kinopoiskId;
    String nameRu;
    Double rating;
    Double ratingKinopoisk;
    int year;
    String posterUrlPreview;

    @Getter
    public enum MovieType {
        TV_SERIES("Сериал"),
        VIDEO("Видео"),
        MINI_SERIES("Мини-сериал"),
        TV_SHOW("Телепередача"),
        FILM("Фильм");

        private final String ruString;

        MovieType(String ruString) {
            this.ruString = ruString;
        }
    }

}
