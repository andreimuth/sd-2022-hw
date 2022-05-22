package com.example.demo.thread;

import com.example.demo.security.dto.MessageResponse;
import com.example.demo.thread.comment.dto.CommentDto;
import com.example.demo.thread.comment.dto.PostCommentDto;
import com.example.demo.thread.dto.CreateThreadDto;
import com.example.demo.thread.dto.SubscribeDto;
import com.example.demo.thread.dto.ThreadDto;
import com.example.demo.thread.dto.UpdateUsersCommentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;


@RestController
@RequestMapping(THREAD)
@RequiredArgsConstructor
public class ThreadController {

    private final ThreadService threadService;

    @GetMapping(FIND_ALL)
    public List<ThreadDto> findAll() {
        return threadService.findAll();
    }

    @PostMapping(ADD_THREAD)
    public CreateThreadDto addThread(@RequestBody  CreateThreadDto threadDto) {
        return threadService.addThread(threadDto);
    }

    @GetMapping(GET_COMMENTS)
    public List<CommentDto> getComments(@PathVariable String id) {
        return threadService.getComments(id);
    }

    @PostMapping(POST_COMMENT)
    public PostCommentDto addComment(@RequestBody PostCommentDto commentDto) {
        return threadService.addComment(commentDto);
    }

    @PatchMapping(SUBSCRIBE)
    public ResponseEntity<MessageResponse> subscribe(@RequestBody SubscribeDto subscribeDto) {
        return ResponseEntity.ok().body(new MessageResponse(threadService.subscribe(subscribeDto)));
    }

    @PatchMapping(UPDATE_USERS_COMMENTS)
    public void updateUsersComments(@RequestBody UpdateUsersCommentsDto dto) {
        if(dto.getThreadId() == null) {
            return;
        }
        threadService.updateUsersComments(dto);
    }

}

