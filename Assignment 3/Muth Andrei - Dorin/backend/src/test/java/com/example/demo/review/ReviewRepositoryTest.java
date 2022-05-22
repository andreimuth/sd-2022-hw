package com.example.demo.review;

import com.example.demo.app.AppRepository;
import com.example.demo.app.model.App;
import com.example.demo.review.model.Review;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static com.example.demo.TestCreationFactory.randomString;
import static com.example.demo.review.Rating.EXCELLENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        appRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void save() {
        User user = User.builder().username(randomString()).password(randomString()).email("a@a").build();
        userRepository.save(user);
        App app = App.builder().name(randomString()).description(randomString()).price(10f).build();
        appRepository.save(app);
        Review review = Review.builder()
                .text("GOOD")
                .rating(EXCELLENT)
                .app(app)
                .user(user)
                .build();
        review = reviewRepository.save(review);
        assertNotNull(review.getId());
    }

    @Test
    void findAllByAppId() {
        User user = User.builder().username(randomString()).password(randomString()).email("a@a").build();
        userRepository.save(user);
        App app = App.builder().name(randomString()).description(randomString()).price(10f).build();
        app = appRepository.save(app);
        Review review = Review.builder()
                .text("GOOD")
                .rating(EXCELLENT)
                .app(app)
                .user(user)
                .build();
        reviewRepository.save(review);
        Set<Review> reviews = reviewRepository.findAllByAppId(app.getId());
        assertEquals(1, reviews.size());
    }

}
