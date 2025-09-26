package org.lucentininicolas.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.lucentininicolas.dtos.WeatherDataDto;
import org.lucentininicolas.entities.WeatherData;
import org.lucentininicolas.repositories.WeatherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class WeatherApiService {
    private static String apiKey = "893fd9d30de145c78c6141654252209";
    private static String getWeatherEndpoint = "https://api.weatherapi.com/v1/current.json";

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private ModelMapper modelMapper;

    public WeatherApiResponseDto GetData() throws JsonProcessingException {
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

        return WeatherApiResponseDto.builder().temp_c(tempC).humidity(humidity).build();
    }
    public List<WeatherDataDto> getWeatherBetweenDates(LocalDate startDate, LocalDate endDate){

       return weatherRepository.getWeatherDateBetweenDates(startDate, endDate)
                .stream()
                .map(x-> modelMapper.map(x, WeatherDataDto.class))
                .collect(Collectors.toList());
    }
    public WeatherDataDto create(WeatherDataDto weatherDataDto){
        weatherRepository.save(modelMapper.map(weatherDataDto,WeatherData.class));
        return weatherDataDto;
    }
    @Scheduled(cron = "0 0 * * * *") // at minute 0 of every hour
    public void cron_getWeatherFromApi() throws JsonProcessingException {
        WeatherApiResponseDto responseDto = GetData();
        WeatherDataDto weatherDataDto = WeatherDataDto.builder()
                .date(LocalDate.now())
                .temperature(responseDto.getTemp_c())
                .humidity(responseDto.getHumidity())
                .build();

        weatherRepository.save(modelMapper.map(weatherDataDto, WeatherData.class));
    }
}
@Builder
@Getter
@Setter
class WeatherApiResponseDto {
    private double temp_c;
    private double humidity;
}

