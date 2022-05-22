package com.example.demo.review.dto;

import com.example.demo.app.dto.AppDto;
import com.example.demo.app.model.App;
import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.model.User;
import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private String text;
    private String rating;
    private UserListDto user;
    private AppDto app;
}
