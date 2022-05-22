package com.example.demo.response;

import com.example.demo.app.dto.AppDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AppDetailsApiResponse {
    private Boolean success;
    private AppDetailsDto data;
}
