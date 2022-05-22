package com.example.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppDto {
    private Long id;
    private String name;
    private String type;
    private String description;
    private Float price = 0.0f;
}
