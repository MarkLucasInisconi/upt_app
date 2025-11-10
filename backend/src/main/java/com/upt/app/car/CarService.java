package com.upt.app.car;

import com.upt.app.common.PageResponse;
import com.upt.app.models.User;
import com.upt.app.rating.Rating;
import com.upt.app.rating.RatingRepository;
import com.upt.app.repositories.UserRepository;
import com.upt.app.car.dto.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;

    public CarService(CarRepository carRepository, UserRepository userRepository, RatingRepository ratingRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    public Car create(Long authorId, CarCreateRequest req) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        Car car = Car.builder()
                .author(author)
                .title(req.title())
                .make(req.make())
                .model(req.model())
                .year(req.year())
                .description(req.description())
                .build();

        return carRepository.save(car);
    }

    public Car getById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
    }

    public Car update(Long id, CarUpdateRequest req) {
        Car car = getById(id);
        car.setTitle(req.title());
        car.setMake(req.make());
        car.setModel(req.model());
        car.setYear(req.year());
        car.setDescription(req.description());
        return carRepository.save(car);
    }

    public void delete(Long id) {
        if (!carRepository.existsById(id)) throw new IllegalArgumentException("Car not found");
        carRepository.deleteById(id);
    }

    public PageResponse<CarResponse> list(String q, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Car> res = (q == null || q.isBlank())
                ? carRepository.findAll(pageable)
                : carRepository.findByMakeContainingIgnoreCaseOrModelContainingIgnoreCase(q, q, pageable);

        // atașează media rating-ului la fiecare rezultat
        Page<CarResponse> mapped = res.map(c -> new CarResponse(c, averageForCar(c.getId())));
        return new PageResponse<>(mapped);
    }

    public double averageForCar(Long carId) {
        List<Rating> list = ratingRepository.findByCarId(carId);
        if (list.isEmpty()) return 0.0;
        double avg = list.stream().mapToInt(Rating::getValue).average().orElse(0.0);
        return Math.round(avg * 100.0) / 100.0;
    }
}
