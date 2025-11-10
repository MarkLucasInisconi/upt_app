package com.upt.app.car;

import com.upt.app.car.dto.*;
import com.upt.app.common.PageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
    public CarController(CarService carService) { this.carService = carService; }

    // Create (authorId din param, până legăm JWT)
    @PostMapping
    public ResponseEntity<?> create(@RequestParam Long authorId, @Valid @RequestBody CarCreateRequest req) {
        try {
            return ResponseEntity.ok(carService.create(authorId, req).getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get by id (include averageRating în CarResponse)
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            var car = carService.getById(id);
            var dto = new CarResponse(car, carService.averageForCar(id));
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // List (paginat) + căutare după make/model
    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            PageResponse<CarResponse> res = carService.list(q, page, size);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CarUpdateRequest req) {
        try {
            return ResponseEntity.ok(carService.update(id, req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            carService.delete(id);
            return ResponseEntity.ok("deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
