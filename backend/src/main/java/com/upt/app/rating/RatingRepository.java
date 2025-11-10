package com.upt.app.rating;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByCarIdAndRaterId(Long carId, Long raterId);
    List<Rating> findByCarId(Long carId);
}
