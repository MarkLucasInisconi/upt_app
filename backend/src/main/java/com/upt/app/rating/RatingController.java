package com.upt.app.rating;

import com.upt.app.rating.dto.RatingResponse;
import com.upt.app.rating.dto.RatingUpsertRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;
    public RatingController(RatingService ratingService) { this.ratingService = ratingService; }

    // Upsert (raterId din param pentru început)
    @PutMapping
    public ResponseEntity<?> upsert(@RequestParam Long raterId, @Valid @RequestBody RatingUpsertRequest req) {
        try {
            var saved = ratingService.upsert(raterId, req);
            return ResponseEntity.ok(saved.getId());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long carId, @RequestParam Long raterId) {
        try {
            ratingService.delete(carId, raterId);
            return ResponseEntity.ok("deleted");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/avg")
    public ResponseEntity<?> average(@RequestParam Long carId) {
        try {
            return ResponseEntity.ok(ratingService.average(carId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
