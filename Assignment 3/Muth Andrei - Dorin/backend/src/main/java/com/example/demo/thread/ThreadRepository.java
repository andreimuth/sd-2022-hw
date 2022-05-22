package com.example.demo.thread;

import com.example.demo.thread.model.Thread;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends MongoRepository<Thread, String> {
}