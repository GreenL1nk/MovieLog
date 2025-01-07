package greenlink.MovieLog.kinopoisk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kinopoisk")
public class KinopoiskProperties {

    private String baseUrl;
    private String apiKey;

}
