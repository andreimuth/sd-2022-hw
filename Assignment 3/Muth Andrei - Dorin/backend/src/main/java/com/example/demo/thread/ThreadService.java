package com.example.demo.thread;

import com.example.demo.thread.comment.dto.CommentDto;
import com.example.demo.thread.comment.dto.PostCommentDto;
import com.example.demo.thread.comment.model.Comment;
import com.example.demo.thread.dto.CreateThreadDto;
import com.example.demo.thread.dto.SubscribeDto;
import com.example.demo.thread.dto.ThreadDto;
import com.example.demo.thread.dto.UpdateUsersCommentsDto;
import com.example.demo.thread.model.Thread;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThreadService {

    private final ThreadRepository threadRepository;
    private final UserRepository userRepository;

    public List<ThreadDto> findAll() {
        return threadRepository.findAll().stream()
                .map(thread -> ThreadDto
                        .builder()
                        .id(thread.getId())
                        .title(thread.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    public Thread findById(String id) {
        return threadRepository.findById(id).orElseThrow(() -> new RuntimeException("Thread with id = " + id + " not found"));
    }

    //public Thread save(Thread thread) {
    //    return threadRepository.save(thread);
    //}

    public CreateThreadDto addThread(CreateThreadDto threadDto) {
        User user = userRepository.findById(threadDto.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User with id = " + threadDto.getUser().getId() + " not found"));
        Comment firstComment = Comment.builder()
                .text(threadDto.getInitialComment())
                .postedAt(LocalDateTime.now())
                .user(user)
                .usersWhoReadTheComment(Set.of(user))
                .build();
        Thread thread = Thread.builder()
                .title(threadDto.getTitle())
                .comments(List.of(firstComment))
                .subscribedUsers(new HashSet<>())
                .build();
        threadRepository.save(thread);
        return threadDto;
    }

    public List<CommentDto> getComments(String id) {
        Thread thread = findById(id);
        return thread.getComments().stream()
                .map(comment -> CommentDto.builder().text(comment.getText())
                        .username(comment.getUser().getUsername()).build())
                .collect(Collectors.toList());
    }

    public PostCommentDto addComment(PostCommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User with id = " + commentDto.getUser().getId() + " not found"));
        Thread thread = findById(commentDto.getThreadId());
        Comment comment = Comment.builder()
                .text(commentDto.getText())
                .postedAt(LocalDateTime.now())
                .user(user)
                .usersWhoReadTheComment(Set.of(user))
                .build();
        thread.getComments().add(comment);
        threadRepository.save(thread);
        return commentDto;
    }

    public String subscribe(SubscribeDto subscribeDto) {
        User user = userRepository.findById(subscribeDto.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User with id = " + subscribeDto.getUser().getId() + " not found"));
        Thread thread = findById(subscribeDto.getThreadId());
        thread.getSubscribedUsers().add(user);
        threadRepository.save(thread);
        return "You have successfully subscribed to " + thread.getTitle() + "!";
    }

    public void updateUsersComments(UpdateUsersCommentsDto dto) {
        Thread thread = findById(dto.getThreadId());
        List<Comment> comments = thread.getComments();
        User user = userRepository.findById(dto.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User with id = " + dto.getUser().getId() + " not found"));
        comments = comments.stream().peek(comment -> comment.getUsersWhoReadTheComment().add(user)).collect(Collectors.toList());
        thread.setComments(comments);
        threadRepository.save(thread);
    }
}

