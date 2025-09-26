package org.lucentininicolas.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WeatherDataDto {
    private LocalDate date;
    private double temperature;
    private double humidity;
}
