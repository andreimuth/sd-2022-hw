package com.example.demo.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class App {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private AppType type;

    @Column(length = 2048)
    @JsonProperty("short_description")
    private String description;

    @Column
    private Float price = 0.0f;

}
