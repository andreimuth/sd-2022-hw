package com.example.demo.review.model;

import com.example.demo.app.model.App;
import com.example.demo.review.Rating;
import com.example.demo.user.model.User;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import static org.hibernate.annotations.CascadeType.*;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String text;
    @Column
    @Enumerated(EnumType.STRING)
    private Rating rating;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "app_id")
    @OnDelete(action = CASCADE)
    private App app;
}
