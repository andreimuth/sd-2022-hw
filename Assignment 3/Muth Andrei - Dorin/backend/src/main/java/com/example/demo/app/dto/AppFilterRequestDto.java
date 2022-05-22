package com.example.demo.app.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppFilterRequestDto {
    @Builder.Default
    private String name = null;
    @Builder.Default
    private String type = null;
    @Builder.Default
    private String description = null;
    @Builder.Default
    private Float maxPrice = -1f;
}
