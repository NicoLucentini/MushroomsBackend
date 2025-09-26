package org.lucentininicolas.repositories;

import org.lucentininicolas.entities.Tanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface TandaRepository extends JpaRepository<Tanda, Integer> , JpaSpecificationExecutor<Tanda> {
    List<Tanda> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);
}
