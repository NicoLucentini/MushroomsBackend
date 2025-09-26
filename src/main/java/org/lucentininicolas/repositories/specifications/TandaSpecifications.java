package org.lucentininicolas.repositories.specifications;

import org.lucentininicolas.entities.Tanda;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TandaSpecifications {

    public static Specification<Tanda> creationDateBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> cb.between(root.get("creationDate"), start, end);
    }

    public static Specification<Tanda> temperatureGreaterThan(double temp) {
        return (root, query, cb) -> cb.greaterThan(root.get("temperature"), temp);
    }

    public static Specification<Tanda> humidityLessThan(double hum) {
        return (root, query, cb) -> cb.lessThan(root.get("humidity"), hum);
    }
}
