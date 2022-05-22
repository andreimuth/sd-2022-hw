package com.example.demo.websockets;

import com.example.demo.thread.ThreadService;
import com.example.demo.thread.comment.dto.PostCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
@RequiredArgsConstructor
public class WebsocketController {
    private final String DESTINATION = "/topic/comments";
    private final ThreadService threadService;

    @MessageMapping("/forum/post-comment")
    @SendTo(DESTINATION)
    public PostCommentDto sendMessage(@Payload PostCommentDto comment) {
        return threadService.addComment(comment);
    }

}
