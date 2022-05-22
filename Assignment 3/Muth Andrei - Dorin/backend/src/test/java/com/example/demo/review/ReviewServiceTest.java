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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.demo.TestCreationFactory.randomLong;
import static com.example.demo.TestCreationFactory.randomString;
import static com.example.demo.review.Rating.EXCELLENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private AppRepository appRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewService = new ReviewService(reviewRepository, appRepository, userRepository);
    }

    @Test
    void save() {
        User user = User.builder().username(randomString()).password(randomString()).email("a@a").build();
        UserListDto userDto = UserListDto.builder().name(user.getUsername()).id(randomLong())
                .password(user.getPassword())
                .email(user.getEmail()).id(user.getId()).build();
        App app = App.builder().id(randomLong()).name(randomString()).description(randomString()).price(10f).build();
        AppDto appDto = AppDto.builder().name(app.getName()).description(app.getDescription())
                .price(app.getPrice()).id(app.getId()).build();
        ReviewDto reviewDto = ReviewDto.builder()
                .text("GOOD")
                .rating("EXCELLENT")
                .app(appDto)
                .user(userDto)
                .build();
        when(appRepository.findById(appDto.getId())).thenReturn(Optional.of(app));
        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(user));
        reviewService.save(reviewDto);
    }

    @Test
    void findReviewsForApp() {
        User user = User.builder().username(randomString()).password(randomString()).email("a@a").build();
        App app = App.builder().name(randomString()).description(randomString()).price(10f).build();
        Review review = Review.builder()
                .text("GOOD")
                .rating(EXCELLENT)
                .app(app)
                .user(user)
                .build();
        long id = randomLong();
        when(reviewRepository.findAllByAppId(id)).thenReturn(Set.of(review));
        List<UIReviewDto> reviews = reviewService.getReviewsForApp(id);
        assertEquals(1, reviews.size());
    }

}
