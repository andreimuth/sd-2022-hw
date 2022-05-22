package com.example.demo.thread;

import com.example.demo.TestCreationFactory;
import com.example.demo.app.model.App;
import com.example.demo.app.model.AppType;
import com.example.demo.app.model.EType;
import com.example.demo.thread.model.Thread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ThreadRepositoryTest {
    @Autowired
    private ThreadRepository threadRepository;

    @BeforeEach
    void beforeEach() {
        threadRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Thread> threads = TestCreationFactory.listOf(Thread.class);
        threadRepository.saveAll(threads);
        List<Thread> all = threadRepository.findAll();
        assertEquals(threads.size(), all.size());
    }

    @Test
    void findById() {
        Thread thread = TestCreationFactory.newThread();
        thread = threadRepository.save(thread);
        Optional<Thread> found = threadRepository.findById(thread.getId());
        assertTrue(found.isPresent());
    }

    @Test
    void save() {
        Thread thread = TestCreationFactory.newThread();
        threadRepository.save(thread);
        assertEquals(1, threadRepository.findAll().size());
    }

}
