package com.example.demo.thread.dto;

import com.example.demo.user.dto.UserListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SubscribeDto {
    private String threadId;
    private UserListDto user;
}
