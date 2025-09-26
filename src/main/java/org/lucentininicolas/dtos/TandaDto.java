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
    private LocalDate endColonizationDate;
    private double kgHarvested;
    private Integer amount;
    private Integer failedAmount;
    private LocalDate startHarvestDate;
    private LocalDate endHarvestDate;
}
