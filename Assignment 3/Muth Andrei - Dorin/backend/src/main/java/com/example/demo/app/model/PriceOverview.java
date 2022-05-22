package com.example.demo.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@NoArgsConstructor
@Getter
@Setter
public class PriceOverview {

    @JsonProperty("final")
    private Float unformattedPrice;

    @Column
    private Float price;

}
