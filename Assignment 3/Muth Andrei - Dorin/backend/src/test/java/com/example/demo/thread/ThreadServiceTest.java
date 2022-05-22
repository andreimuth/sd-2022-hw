package com.example.demo.thread;

import com.example.demo.TestCreationFactory;
import com.example.demo.app.AppService;
import com.example.demo.app.dto.AppDto;
import com.example.demo.app.model.App;
import com.example.demo.thread.comment.dto.CommentDto;
import com.example.demo.thread.comment.dto.PostCommentDto;
import com.example.demo.thread.dto.CreateThreadDto;
import com.example.demo.thread.dto.SubscribeDto;
import com.example.demo.thread.dto.ThreadDto;
import com.example.demo.thread.dto.UpdateUsersCommentsDto;
import com.example.demo.thread.model.Thread;
import com.example.demo.user.UserRepository;
import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.example.demo.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ThreadServiceTest {

    @InjectMocks
    private ThreadService threadService;
    @Mock
    private ThreadRepository threadRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        threadService = new ThreadService(threadRepository, userRepository);
    }

    @Test
    void findAll() {
        List<Thread> threads = TestCreationFactory.listOf(Thread.class);
        when(threadRepository.findAll()).thenReturn(threads);

        List<ThreadDto> all = threadService.findAll();

        assertEquals(threads.size(), all.size());
    }

    @Test
    void addThread() {
        User user = User.builder().username(randomString()).password(randomString()).email("a@a").build();
        UserListDto userListDto = UserListDto.builder().email(user.getEmail()).build();
        long userId = randomLong();
        userListDto.setId(userId);
        CreateThreadDto threadDto = TestCreationFactory.newCreateThreadDto();
        threadDto.setUser(userListDto);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Thread thread = TestCreationFactory.newThread();
        when(threadRepository.save(thread)).thenReturn(thread);
        when(threadService.addThread(threadDto)).thenReturn(threadDto);
        assertEquals(threadDto, threadService.addThread(threadDto));
    }

    @Test
    void findById() {
        Thread thread = TestCreationFactory.newThread();
        String id = randomString();
        when(threadRepository.findById(id)).thenReturn(Optional.ofNullable(thread));
        assertEquals(thread, threadService.findById(id));
    }

    @Test
    void getComments() {
        Thread thread = TestCreationFactory.newThread();
        thread.setComments(List.of());
        String id = randomString();
        when(threadRepository.findById(id)).thenReturn(Optional.of(thread));
        assertEquals(0, threadService.getComments(id).size());
    }

    @Test
    void addComment() {
        Thread thread = TestCreationFactory.newThread();
        thread.setComments(new ArrayList<>());
        User user = User.builder().username(randomString())
                .password(randomString()).email("a@a").build();
        long userId = randomLong();
        UserListDto userListDto = UserListDto.builder().id(userId).email(user.getEmail()).build();
        PostCommentDto comment = TestCreationFactory.newPostCommentDto();
        comment.setUser(userListDto);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(threadRepository.findById(comment.getThreadId())).thenReturn(Optional.of(thread));
        when(threadRepository.save(thread)).thenReturn(thread);
        threadService.addComment(comment);
        assertEquals(comment, comment);
    }

    @Test
    void subscribe() {
        User user = User.builder().username(randomString())
                .password(randomString()).email("a@a").build();
        long userId = randomLong();
        UserListDto userListDto = UserListDto.builder().id(userId).email(user.getEmail()).build();
        SubscribeDto subscription = SubscribeDto.builder().user(userListDto).threadId(randomString()).build();
        Thread thread = TestCreationFactory.newThread();
        thread.setSubscribedUsers(new HashSet<>());
        when(userRepository.findById(subscription.getUser().getId())).thenReturn(Optional.of(user));
        when(threadRepository.findById(subscription.getThreadId())).thenReturn(Optional.of(thread));
        when(threadRepository.save(thread)).thenReturn(thread);
        assertEquals("You have successfully subscribed to " + thread.getTitle() + "!",
                threadService.subscribe(subscription));
    }

    @Test
    void updateUsersComments() {
        User user = User.builder().username(randomString())
                .password(randomString()).email("a@a").build();
        long userId = randomLong();
        UserListDto userListDto = UserListDto.builder().id(userId).email(user.getEmail()).build();
        UpdateUsersCommentsDto usersCommentsDto = UpdateUsersCommentsDto.builder().threadId(randomString())
                .user(userListDto).build();
        Thread thread = TestCreationFactory.newThread();
        thread.setComments(new ArrayList<>());
        when(threadRepository.findById(usersCommentsDto.getThreadId())).thenReturn(Optional.of(thread));
        when(userRepository.findById(usersCommentsDto.getUser().getId())).thenReturn(Optional.of(user));
        threadService.updateUsersComments(usersCommentsDto);
    }

}
