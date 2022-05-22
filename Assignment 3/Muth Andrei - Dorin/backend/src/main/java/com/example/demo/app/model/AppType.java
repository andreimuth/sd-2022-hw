package com.example.demo.app.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AppType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private EType name;

}
