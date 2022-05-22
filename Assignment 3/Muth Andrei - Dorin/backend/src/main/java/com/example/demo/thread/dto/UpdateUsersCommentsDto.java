package com.example.demo.thread.dto;

import com.example.demo.user.dto.UserListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUsersCommentsDto {
    private UserListDto user;
    private String threadId;
}
