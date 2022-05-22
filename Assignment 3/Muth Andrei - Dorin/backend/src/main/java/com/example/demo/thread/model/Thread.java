package com.example.demo.thread.model;

import com.example.demo.thread.comment.model.Comment;
import com.example.demo.user.model.User;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;
import java.util.Set;

@Document(collection = "Thread")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Thread {
    @Id
    private String id;
    private String title;
    private List<Comment> comments;
    private Set<User> subscribedUsers;
}
