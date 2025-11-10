package com.upt.app.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByMakeContainingIgnoreCaseOrModelContainingIgnoreCase(String make, String model, Pageable pageable);
}
