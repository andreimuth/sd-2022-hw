package com.example.demo.review;

import com.example.demo.review.dto.ReviewDto;
import com.example.demo.review.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.UrlMapping.ADD_REVIEW;
import static com.example.demo.UrlMapping.REVIEW;


@RestController
@RequiredArgsConstructor
@RequestMapping(REVIEW)
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(ADD_REVIEW)
    public void addReview(@RequestBody ReviewDto review) {
        reviewService.save(review);
    }

}
