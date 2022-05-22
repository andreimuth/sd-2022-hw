package com.example.demo.review;

import com.example.demo.app.AppRepository;
import com.example.demo.app.dto.AppDto;
import com.example.demo.app.model.App;
import com.example.demo.review.dto.ReviewDto;
import com.example.demo.review.dto.UIReviewDto;
import com.example.demo.review.model.Review;
import com.example.demo.user.UserRepository;
import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.demo.TestCreationFactory.randomString;
import static com.example.demo.review.Rating.EXCELLENT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    @AfterEach
    void cleanup() {
        reviewRepository.deleteAll();
        appRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getReviewsForItem() {
        User user = User.builder().username(randomString()).password(randomString()).email("a@a").build();
        userRepository.save(user);
        App app = App.builder().name(randomString()).description(randomString()).price(10f).build();
        app = appRepository.save(app);

        Review reviewToBeAdded = Review.builder().app(app).user(user).text(randomString())
                .rating(EXCELLENT).build();
        reviewRepository.save(reviewToBeAdded);

        List<UIReviewDto> reviewsForItem = reviewService.getReviewsForApp(app.getId());

        assertEquals(1, reviewsForItem.size());
    }

    @Test
    void save() {
        User user = User.builder().username(randomString()).password(randomString()).email("a@a").build();
        user = userRepository.save(user);
        UserListDto userDto = UserListDto.builder().name(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail()).id(user.getId()).build();
        App app = App.builder().name(randomString()).description(randomString()).price(10f).build();
        app = appRepository.save(app);
        AppDto appDto = AppDto.builder().name(app.getName()).description(app.getDescription())
                .price(app.getPrice()).id(app.getId()).build();
        ReviewDto reviewDto = ReviewDto.builder()
                .text("GOOD")
                .rating("EXCELLENT")
                .app(appDto)
                .user(userDto)
                .build();
        reviewService.save(reviewDto);
    }

}
