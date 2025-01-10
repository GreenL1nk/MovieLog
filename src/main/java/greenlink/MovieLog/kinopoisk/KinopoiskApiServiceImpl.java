package greenlink.MovieLog.kinopoisk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import greenlink.MovieLog.movie.Movie;
import greenlink.MovieLog.movie.SearchedMovie;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KinopoiskApiServiceImpl implements KinopoiskApiService {

    private final RestTemplate restTemplate;
    private final KinopoiskProperties properties;

    public KinopoiskApiServiceImpl(RestTemplate restTemplate, KinopoiskProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }


    @Override
    public Movie getMovieById(String id) {

        String url = UriComponentsBuilder
                .fromUriString(properties.getBaseUrl() + "/v2.2/films/" + id)
                .build(false)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", properties.getApiKey());
        headers.add("accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode filmNode = objectMapper.readTree(response.getBody());

            return objectMapper.readValue(filmNode.toString(), Movie.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Ошибка при запросе: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при запросе к API: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<SearchedMovie> getMoviesByName(String endpoint, Map<String, List<String>> queryParams) {

        String url = UriComponentsBuilder
                .fromUriString(properties.getBaseUrl() + endpoint)
                .queryParams(CollectionUtils.toMultiValueMap(queryParams))
                .build(false)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", properties.getApiKey());
        headers.add("accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode filmsNode = objectMapper.readTree(response.getBody()).get("films");

            return objectMapper.readValue(filmsNode.toString(), new TypeReference<>() {
            });
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Ошибка при запросе: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при запросе к API: " + e.getMessage(), e);
        }
    }
}
