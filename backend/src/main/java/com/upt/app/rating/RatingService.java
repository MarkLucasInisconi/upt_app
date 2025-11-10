package com.upt.app.rating;

import com.upt.app.car.Car;
import com.upt.app.car.CarRepository;
import com.upt.app.models.User;
import com.upt.app.repositories.UserRepository;
import com.upt.app.rating.dto.RatingUpsertRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public RatingService(RatingRepository ratingRepository, CarRepository carRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    // upsert (un rating per (car, user))
    public Rating upsert(Long raterId, RatingUpsertRequest req) {
        if (req.value() < 1 || req.value() > 5) {
            throw new IllegalArgumentException("value must be in [1..5]");
        }

        Car car = carRepository.findById(req.postId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        User rater = userRepository.findById(raterId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Rating rating = ratingRepository.findByCarIdAndRaterId(car.getId(), rater.getId())
                .orElseGet(Rating::new);

        rating.setCar(car);
        rating.setRater(rater);
        rating.setValue(req.value());

        return ratingRepository.save(rating);
    }

    public void delete(Long carId, Long raterId) {
        Rating r = ratingRepository.findByCarIdAndRaterId(carId, raterId)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));
        ratingRepository.delete(r);
    }

    public double average(Long carId) {
        List<Rating> list = ratingRepository.findByCarId(carId);
        if (list.isEmpty()) return 0.0;
        double avg = list.stream().mapToInt(Rating::getValue).average().orElse(0.0);
        return Math.round(avg * 100.0) / 100.0;
    }
}
