package com.example.demo.review;

import com.example.demo.app.AppRepository;
import com.example.demo.app.mapper.AppMapper;
import com.example.demo.app.model.App;
import com.example.demo.review.dto.ReviewDto;
import com.example.demo.review.dto.UIReviewDto;
import com.example.demo.review.model.Review;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppRepository appRepository;
    private final UserRepository userRepository;

    public ReviewDto save(ReviewDto reviewDto) {
        App app = appRepository.findById(reviewDto.getApp().getId())
                .orElseThrow(() -> new RuntimeException("App not found"));
        User user = userRepository.findById(reviewDto.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Review review = Review.builder()
                .text(reviewDto.getText())
                .rating(Rating.valueOf(reviewDto.getRating()))
                .user(user)
                .app(app)
                .build();
        Review savedReview = reviewRepository.save(review);
        return reviewDto;
    }

    public List<UIReviewDto> getReviewsForApp(Long id) {
        return reviewRepository.findAllByAppId(id)
                .stream()
                .map(review -> UIReviewDto.builder()
                        .text(review.getText())
                        .rating(review.getRating().name())
                        .build())
                .collect(Collectors.toList());
    }
}
