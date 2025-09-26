package org.lucentininicolas.repositories;

import org.lucentininicolas.entities.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherData, Integer> {

    @Query("SELECT w FROM WeatherData w WHERE w.date >= :startDate AND w.date <= :endDate")
    List<WeatherData> getWeatherDateBetweenDates(@Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}
