package com.upt.app.comment;

import com.upt.app.comment.comment_dto.*;
import com.upt.app.common.PageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) { this.commentService = commentService; }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam Long userId, @Valid @RequestBody CommentCreateRequest req) {
        try {
            return ResponseEntity.ok(commentService.create(userId, req).getId());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestParam Long userId, @Valid @RequestBody CommentUpdateRequest req) {
        try {
            return ResponseEntity.ok(commentService.update(id, userId, req));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam Long userId) {
        try {
            commentService.delete(id, userId);
            return ResponseEntity.ok("deleted");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<?> listForCar(@PathVariable Long carId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        try {
            PageResponse<Comment> res = commentService.listForCar(carId, page, size);
            return ResponseEntity.ok(res);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
