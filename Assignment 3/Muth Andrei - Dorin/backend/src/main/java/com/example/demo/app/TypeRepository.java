package com.example.demo.app;

import com.example.demo.app.model.AppType;
import com.example.demo.app.model.EType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<AppType, Long> {
    Optional<AppType> findByName(EType name);
}
