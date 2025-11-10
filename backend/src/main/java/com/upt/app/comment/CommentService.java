package com.upt.app.comment;

import com.upt.app.car.Car;
import com.upt.app.car.CarRepository;
import com.upt.app.common.PageResponse;
import com.upt.app.models.User;
import com.upt.app.repositories.UserRepository;
import com.upt.app.comment.comment_dto.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, CarRepository carRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public Comment create(Long userId, CommentCreateRequest req) {
        Car car = carRepository.findById(req.postId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment c = Comment.builder()
                .car(car)
                .user(user)
                .content(req.content())
                .build();

        return commentRepository.save(c);
    }

    public Comment update(Long id, Long userId, CommentUpdateRequest req) {
        Comment c = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        if (!c.getUser().getId().equals(userId))
            throw new IllegalArgumentException("Nu poți edita comentariul altui utilizator");
        c.setContent(req.content());
        return commentRepository.save(c);
    }

    public void delete(Long id, Long userId) {
        Comment c = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        if (!c.getUser().getId().equals(userId))
            throw new IllegalArgumentException("Nu poți șterge comentariul altui utilizator");
        commentRepository.delete(c);
    }

    public PageResponse<Comment> listForCar(Long carId, int page, int size) {
        Pageable p = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return new PageResponse<>(commentRepository.findByCarId(carId, p));
    }
}
