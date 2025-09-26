package org.lucentininicolas.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Tandas")
public class Tanda {
    @Id
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
