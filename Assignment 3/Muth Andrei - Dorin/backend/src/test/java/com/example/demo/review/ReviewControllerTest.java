package com.example.demo.review;

import com.example.demo.BaseControllerTest;
import com.example.demo.app.AppController;
import com.example.demo.app.dto.AppDto;
import com.example.demo.review.dto.ReviewDto;
import com.example.demo.user.dto.UserListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewControllerTest extends BaseControllerTest {

    @InjectMocks
    private ReviewController reviewController;
    @Mock
    private ReviewService reviewService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        reviewController = new ReviewController(reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void createReview() throws Exception {
        ReviewDto reviewDto = ReviewDto.builder()
                        .text("text").rating("EXCELLENT").build();

        when(reviewService.save(reviewDto)).thenReturn(reviewDto);

        ResultActions result = performPostWithRequestBody(REVIEW + ADD_REVIEW, reviewDto);
        result.andExpect(status().isOk());
    }

}
