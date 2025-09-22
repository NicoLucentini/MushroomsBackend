package org.lucentininicolas.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class WeatherApiService {
    private static String apiKey = "893fd9d30de145c78c6141654252209";
    private static String getWeatherEndpoint = "https://api.weatherapi.com/v1/current.json";
    public WeatherResponseDto GetData() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        // Build the URL with query parameters
        String url = UriComponentsBuilder.fromHttpUrl(getWeatherEndpoint)
                .queryParam("q", "Vistalba")
                .queryParam("key", apiKey)
                .toUriString();

        String json = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        double tempC = root.path("current").path("temp_c").asDouble();
        double humidity = root.path("current").path("humidity").asDouble();

        return WeatherResponseDto.builder().temp_c(tempC).humidity(humidity).build();
    }
}
@Builder
@Getter
@Setter
class WeatherResponseDto{
    public double temp_c;
    public double humidity;
}

