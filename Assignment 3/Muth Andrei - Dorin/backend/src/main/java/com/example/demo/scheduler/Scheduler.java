package com.example.demo.scheduler;

import com.example.demo.email.EmailSender;
import com.example.demo.thread.ThreadRepository;
import com.example.demo.thread.comment.model.Comment;
import com.example.demo.thread.model.Thread;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ThreadRepository threadRepository;
    private final EmailSender emailSender;

    //@Scheduled(cron = "0 0/3 * * * ?")
    @Scheduled(cron = "@daily")
    public void scheduler() {
        List<Thread> threads = threadRepository.findAll();
        for(Thread thread : threads) {
            for(User user : thread.getSubscribedUsers()) {
                List<Comment> unreadComments = thread.getComments().stream()
                        .filter(comment -> !comment.getUsersWhoReadTheComment().contains(user)).collect(Collectors.toList());
                StringBuilder message = new StringBuilder();
                for(Comment comment : unreadComments) {
                    message.append(comment.getUser().getUsername()).append(": ").append(comment.getText()).append("\n");
                    comment.getUsersWhoReadTheComment().add(user);
                }
                if(!message.toString().isBlank()) {
                    emailSender.sendEmail(user.getEmail(), "What you missed on " + thread.getTitle(), message.toString());
                }
            }
            threadRepository.save(thread);
        }
    }

}