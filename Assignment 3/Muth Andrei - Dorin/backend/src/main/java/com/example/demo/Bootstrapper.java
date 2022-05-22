package com.example.demo;

import com.example.demo.app.AppRepository;
import com.example.demo.app.TypeRepository;
import com.example.demo.app.model.AppType;
import com.example.demo.app.model.EType;
import com.example.demo.review.ReviewRepository;
import com.example.demo.thread.ThreadRepository;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;
    private final AppRepository appRepository;
    private final UserRepository userRepository;
    private final TypeRepository typeRepository;
    private final ReviewRepository reviewRepository;
    private final ThreadRepository threadRepository;
    private final PasswordEncoder encoder;


    @Value("${app.bootstrap}")
    private Boolean bootstrap = true;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrap) {
            System.out.println("Bootstrapping...");
            reviewRepository.deleteAll();
            appRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            typeRepository.deleteAll();
            threadRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            for(EType value : EType.values()) {
                typeRepository.save(
                        AppType.builder().name(value).build()
                );
            }
            Role adminRole = roleRepository.findByName(ERole.ADMIN).orElseThrow(() -> new RuntimeException("Admin role not found"));
            Set<Role> roles = Set.of(adminRole);
            User adminUser = User.builder()
                    .email("admin@admin.a")
                    .username("admin")
                    .password(encoder.encode("admin"))
                    .roles(roles)
                    .build();
            userRepository.save(adminUser);
        }
    }
}