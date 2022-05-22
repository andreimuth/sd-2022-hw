package com.example.demo.app.model;

import com.example.demo.app.dto.ApiCallAppDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppList {
    private List<ApiCallAppDto> apps;
}
