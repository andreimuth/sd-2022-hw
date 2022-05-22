package com.example.demo.thread.comment.dto;

import com.example.demo.thread.dto.ThreadDto;
import com.example.demo.user.dto.UserListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class PostCommentDto {
    private String text;
    private String threadId;
    private UserListDto user;
}
