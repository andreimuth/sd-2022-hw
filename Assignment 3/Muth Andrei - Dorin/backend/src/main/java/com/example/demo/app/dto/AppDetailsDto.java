package com.example.demo.app.dto;

import com.example.demo.app.model.PriceOverview;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AppDetailsDto {
    private String name;
    private String type;
    @JsonProperty("short_description")
    private String description;
    @JsonProperty("price_overview")
    private PriceOverview priceOverview;
}
