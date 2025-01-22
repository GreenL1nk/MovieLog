package greenlink.MovieLog.top;

import lombok.Data;
import lombok.Getter;

@Data
public class TopCollection {

    private final TopType topType;

    @Getter
    public enum TopType {
        TOP_POPULAR_ALL("Популярное"),
        TOP_POPULAR_MOVIES("Популярные фильмы"),
        TOP_250_TV_SHOWS("250 самых популярных сериалов"),
        TOP_250_MOVIES("250 самых популярных фильмов"),
        VAMPIRE_THEME("Вампиры"),
        COMICS_THEME("Комиксы");

        String ruName;

        TopType(String ruName) {
            this.ruName = ruName;
        }
    }

}
