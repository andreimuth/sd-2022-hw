package com.example.demo.app;

import com.example.demo.TestCreationFactory;
import com.example.demo.app.dto.AppDto;
import com.example.demo.app.dto.AppFilterRequestDto;
import com.example.demo.app.model.App;
import com.example.demo.app.model.AppType;
import com.example.demo.app.model.EType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.TestCreationFactory.randomString;
import static com.example.demo.app.model.EType.GAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AppServiceIntegrationTest {

    @Autowired
    private AppService appService;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private TypeRepository typeRepository;

    @BeforeEach
    void setUp() {
        appRepository.deleteAll();
        typeRepository.deleteAll();
        for(EType value : EType.values()) {
            typeRepository.save(
                    AppType.builder().name(value).build()
            );
        }
    }

    @Test
    void findAll() {
        List<App> apps = TestCreationFactory.listOf(App.class);
        apps = apps.stream().peek(app -> {
            AppType type = typeRepository.findByName(GAME).get();
            app.setType(type);
        }).collect(Collectors.toList());
        appRepository.saveAll(apps);
        List<AppDto> all = appService.findAll();

        assertEquals(apps.size(), all.size());
    }

    @Test
    void importApps() throws Exception {
        List<AppDto> apps = appService.importApps();
        assertTrue(apps.size() > 0);
    }

    @Test
    void findTypeByName() {
        AppType type = appService.findTypeByName("GAME");
        assertEquals(type.getName(), GAME);
    }

    @Test
    void create() {
        AppDto appDto = TestCreationFactory.newAppDto();

        AppDto savedApp = appService.create(appDto);

        assertNotNull(savedApp.getId());
        assertEquals(savedApp.getName(), appDto.getName());
    }

    @Test
    void update() {
        AppDto initial = TestCreationFactory.newAppDto();
        AppDto saved = appService.create(initial);
        AppDto updateToThis = AppDto.builder().name(randomString())
                .description(randomString())
                .price(15f).type("GAME").build();
        AppDto updated = appService.update(saved.getId(), updateToThis);
        assertEquals(updated.getName(), updateToThis.getName());
        assertEquals(updated.getDescription(), updateToThis.getDescription());
        assertEquals(updated.getPrice(), updateToThis.getPrice());
        assertEquals(updated.getType(), updateToThis.getType());
    }

    @Test
    void findById() {
        AppDto appDto = TestCreationFactory.newAppDto();
        AppDto saved = appService.create(appDto);
        App app = appService.findById(saved.getId());
        assertEquals(app.getName(), saved.getName());
    }

    @Test
    void delete() {
        List<App> apps = TestCreationFactory.listOf(App.class);
        int size = apps.size();
        appRepository.saveAll(apps);
        List<App> saved = appRepository.findAll();
        Long id = saved.get(0).getId();
        appService.delete(id);
        assertEquals(size - 1, appRepository.findAll().size());
    }

    @Test
    void findAllFiltered() {
        AppDto app1 = AppDto.builder().name("abc").description("tralala")
                .type("GAME").price(1.99F).build();
        AppDto app2 = AppDto.builder().name("def").description("tralala2")
                .type("DLC").price(2.99F).build();
        appService.create(app1);
        appService.create(app2);
        AppFilterRequestDto filter = AppFilterRequestDto.builder().name("bla").build();
        List<AppDto> filtered = appService.findAllFiltered(filter);
        assertEquals(0, filtered.size());
    }

}
