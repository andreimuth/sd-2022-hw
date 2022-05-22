package com.example.demo.thread;

import com.example.demo.TestCreationFactory;
import com.example.demo.thread.comment.dto.CommentDto;
import com.example.demo.thread.comment.dto.PostCommentDto;
import com.example.demo.thread.comment.model.Comment;
import com.example.demo.thread.dto.CreateThreadDto;
import com.example.demo.thread.dto.SubscribeDto;
import com.example.demo.thread.dto.ThreadDto;
import com.example.demo.thread.model.Thread;
import com.example.demo.user.UserRepository;
import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.example.demo.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ThreadServiceIntegrationTest {

    @Autowired
    private ThreadService threadService;
    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    @AfterEach
    void cleanup() {
        threadRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Thread> threads = TestCreationFactory.listOf(Thread.class);
        threadRepository.saveAll(threads);
        List<ThreadDto> dtos = threadService.findAll();
        assertEquals(dtos.size(), threads.size());
    }

    @Test
    void addThread() {
        User user = User.builder().username(randomString()).password(randomString()).email("a@a").build();
        user = userRepository.save(user);
        UserListDto userDto = UserListDto.builder().id(user.getId()).build();
        CreateThreadDto threadDto = TestCreationFactory.newCreateThreadDto();
        threadDto.setUser(userDto);
        threadService.addThread(threadDto);
        assertEquals(1, threadRepository.findAll().size());
    }

    @Test
    void getComments() {
        User user = User.builder().username(randomString()).password(randomString()).email("a@a").build();
        user = userRepository.save(user);
        Thread thread = TestCreationFactory.newThread();
        Comment comment = Comment.builder().text(randomString()).user(user).build();
        thread.setComments(List.of(comment));
        threadRepository.save(thread);
        List<CommentDto> comments = threadService.getComments(thread.getId());
        assertEquals(1, comments.size());
    }

    @Test
    void addComment() {
        Thread thread = TestCreationFactory.newThread();
        thread.setComments(List.of());
        thread = threadRepository.save(thread);
        User user = User.builder().username(randomString()).password(randomString()).email("a@a").build();
        user = userRepository.save(user);
        UserListDto userDto = UserListDto.builder().id(user.getId()).build();
        PostCommentDto comment = TestCreationFactory.newPostCommentDto();
        comment.setThreadId(thread.getId());
        comment.setUser(userDto);
        threadService.addComment(comment);
        Optional<Thread> updated = threadRepository.findById(thread.getId());
        assertTrue(updated.isPresent());
        assertEquals(1, updated.get().getComments().size());
    }

    @Test
    void findById() {
        Thread thread = TestCreationFactory.newThread();
        thread = threadRepository.save(thread);
        Thread found = threadService.findById(thread.getId());
        assertEquals(found.getId(), thread.getId());
        assertEquals(found.getTitle(), thread.getTitle());
    }

    @Test
    void subscribe() {
        Thread thread = TestCreationFactory.newThread();
        thread.setSubscribedUsers(new HashSet<>());
        thread = threadRepository.save(thread);
        User user = User.builder().username(randomString()).password(randomString()).email("a@a").build();
        user = userRepository.save(user);
        UserListDto userDto = UserListDto.builder().id(user.getId()).build();
        SubscribeDto subscription = SubscribeDto.builder().threadId(thread.getId()).user(userDto).build();
        threadService.subscribe(subscription);
        Optional<Thread> updated = threadRepository.findById(thread.getId());
        assertTrue(updated.isPresent());
        assertEquals(1, updated.get().getSubscribedUsers().size());
    }

}
