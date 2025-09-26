package org.lucentininicolas.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.lucentininicolas.ApplicationConfig;
import org.lucentininicolas.dtos.WeatherDataDto;
import org.lucentininicolas.services.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(ApplicationConfig.class)
public class WeatherControllerIntegrationTest {
    private static final String WEATHER_PATH = "/weather";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherApiService weatherApiService;

    @Test
    void shouldReturnWeatherDatesBetween() throws Exception {

        WeatherDataDto weather1 = WeatherDataDto.builder().date(LocalDate.of(2025,01,02)).temperature(20).humidity(10).build();
        WeatherDataDto weather2 = WeatherDataDto.builder().date(LocalDate.of(2025,02,02)).temperature(30).humidity(10).build();
        WeatherDataDto weather3 = WeatherDataDto.builder().date(LocalDate.of(2025,03,02)).temperature(20).humidity(10).build();

        weatherApiService.create(weather1);
        weatherApiService.create(weather2);
        weatherApiService.create(weather3);

        mockMvc.perform(get(WEATHER_PATH  + "/between")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2025-02-28"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(weather1, weather2))));


    }
}
