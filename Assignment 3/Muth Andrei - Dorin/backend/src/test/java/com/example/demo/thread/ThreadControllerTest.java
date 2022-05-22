package com.example.demo.thread;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.app.AppController;
import com.example.demo.app.dto.AppDto;
import com.example.demo.app.model.App;
import com.example.demo.security.dto.MessageResponse;
import com.example.demo.thread.comment.dto.CommentDto;
import com.example.demo.thread.comment.dto.PostCommentDto;
import com.example.demo.thread.dto.CreateThreadDto;
import com.example.demo.thread.dto.SubscribeDto;
import com.example.demo.thread.dto.ThreadDto;
import com.example.demo.thread.dto.UpdateUsersCommentsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThreadControllerTest extends BaseControllerTest {

    @InjectMocks
    private ThreadController threadController;

    @Mock
    private ThreadService threadService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        threadController = new ThreadController(threadService);
        mockMvc = MockMvcBuilders.standaloneSetup(threadController).build();
    }

    @Test
    void findAll() throws Exception {
        List<ThreadDto> threads = listOf(ThreadDto.class);
        when(threadService.findAll()).thenReturn(threads);
        ResultActions response = mockMvc.perform(get(THREAD + FIND_ALL));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(threads));
    }

    @Test
    void create() throws Exception {
        CreateThreadDto reqThread = TestCreationFactory.newCreateThreadDto();

        when(threadService.addThread(reqThread)).thenReturn(reqThread);

        ResultActions result = performPostWithRequestBody(THREAD + ADD_THREAD, reqThread);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqThread));
    }

    @Test
    void getComments() throws Exception {
        List<CommentDto> comments = TestCreationFactory.listOf(CommentDto.class);
        String id = randomString();
        when(threadService.getComments(id)).thenReturn(comments);

        ResultActions result = performGetWithPathVariable(THREAD + GET_COMMENTS, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(comments));
    }

    @Test
    void addComment() throws Exception {
        PostCommentDto comment = TestCreationFactory.newPostCommentDto();
        when(threadService.addComment(comment)).thenReturn(comment);
        ResultActions result = performPostWithRequestBody(THREAD + POST_COMMENT, comment);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(comment));
    }

    @Test
    void subscribe() throws Exception {
        SubscribeDto subscription = SubscribeDto.builder().threadId(randomString()).build();
        String response = String.valueOf(ResponseEntity.ok().body(new MessageResponse("success")));
        when(threadService.subscribe(subscription)).thenReturn(
                response
        );
        ResultActions result = performPatchWithRequestBody(THREAD + SUBSCRIBE, subscription);
        result.andExpect(status().isOk());
    }

    @Test
    void updateUsersComments() throws Exception {
        UpdateUsersCommentsDto usersCommentsDto = UpdateUsersCommentsDto.builder().build();
        doNothing().when(threadService).updateUsersComments(usersCommentsDto);
        ResultActions result = performPatchWithRequestBody(THREAD + UPDATE_USERS_COMMENTS,
                usersCommentsDto);
        result.andExpect(status().isOk());
    }

}
