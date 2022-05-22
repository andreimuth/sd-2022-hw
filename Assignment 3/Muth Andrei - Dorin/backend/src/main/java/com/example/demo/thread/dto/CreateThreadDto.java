package com.example.demo.thread.dto;

import com.example.demo.user.dto.UserListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateThreadDto {
    private String title;
    private String initialComment;
    private UserListDto user;
}
