package org.lucentininicolas.controllers;

import org.lucentininicolas.dtos.WeatherDataDto;
import org.lucentininicolas.services.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("weather")
public class WeatherController {
    @Autowired
    private WeatherApiService weatherApiService;
    @GetMapping("/between")
    public List<WeatherDataDto> getWeatherBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    )
    {
        return weatherApiService.getWeatherBetweenDates(startDate, endDate);
    }
    @PostMapping("/create")
    public WeatherDataDto create(@RequestBody WeatherDataDto weatherDataDto)
    {
        return weatherApiService.create(weatherDataDto);
    }
}
