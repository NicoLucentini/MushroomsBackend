package org.lucentininicolas.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TandaDto {
    private Integer id;
    private LocalDate creationDate;
    private double temperature;
    private double humidity;
}
