package com.example.demo.thread.comment.dto;

import com.example.demo.user.dto.UserListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CommentDto {
    private String text;
    private String username;
}
