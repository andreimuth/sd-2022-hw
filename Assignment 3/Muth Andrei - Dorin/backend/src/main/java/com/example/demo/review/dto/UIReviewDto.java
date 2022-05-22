package com.example.demo.review.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UIReviewDto {
    private String text;
    private String rating;
}
