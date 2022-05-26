package com.example.demo.app;

import com.example.demo.TestCreationFactory;
import com.example.demo.app.model.App;
import com.example.demo.app.model.AppType;
import com.example.demo.app.model.EType;
import com.example.demo.review.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.example.demo.app.AppSpecifications.similarNames;
import static com.example.demo.app.model.EType.GAME;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppRepositoryTest {

    @Autowired
    private AppRepository appRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private TypeRepository typeRepository;

    @BeforeEach
    public void beforeEach() {
        reviewRepository.deleteAll();
        appRepository.deleteAll();
        typeRepository.deleteAll();
        for(EType value : EType.values()) {
            typeRepository.save(
                    AppType.builder().name(value).build()
            );
        }
    }

    @Test
    void testFindAll() {
        List<App> apps = TestCreationFactory.listOf(App.class);
        appRepository.saveAll(apps);
        List<App> all = appRepository.findAll();
        assertEquals(apps.size(), all.size());
    }

    @Test
    void create() {
        App app = TestCreationFactory.newApp();
        appRepository.save(app);
        assertEquals(1, appRepository.findAll().size());
    }

    @Test
    void delete() {
        List<App> apps = TestCreationFactory.listOf(App.class);
        appRepository.saveAll(apps);
        List<App> saved = appRepository.findAll();
        appRepository.deleteById(saved.get(0).getId());
        assertEquals(apps.size() - 1, appRepository.findAll().size());
    }

    @Test
    void findTypeByName() {
        Optional<AppType> type = typeRepository.findByName(GAME);
        assertTrue(type.isPresent());
        assertEquals(type.get().getName(), GAME);
    }

    @Test
    void findById() {
        App app = TestCreationFactory.newApp();
        App saved = appRepository.save(app);
        Optional<App> found = appRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(saved, found.get());
    }

    @Test
    void testSpecificationQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String name = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    appRepository.save(App.builder()
                            .name(name)
                            .build());
                }
            }
        }

        final List<App> apps = appRepository.findAll(similarNames("%b%"));
        assertTrue(apps.size() > 1000);
    }

}
