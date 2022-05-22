package com.example.demo.thread.comment.model;

import com.example.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Comment {

    private String text;
    private LocalDateTime postedAt;
    private User user;
    private Set<User> usersWhoReadTheComment;

}
