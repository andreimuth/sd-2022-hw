package com.example.demo.app;

import com.example.demo.TestCreationFactory;
import com.example.demo.app.dto.ApiCallAppDto;
import com.example.demo.app.dto.AppDto;
import com.example.demo.app.dto.AppFilterRequestDto;
import com.example.demo.app.mapper.AppMapper;
import com.example.demo.app.model.App;
import com.example.demo.app.model.AppType;
import com.example.demo.pdf.PDFGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.app.model.EType.GAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class AppServiceTest {

    @InjectMocks
    private AppService appService;

    @Mock
    private AppRepository appRepository;

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private AppMapper appMapper;

    @Mock
    private  ObjectMapper objectMapper;

    @Mock
    private PDFGenerator pdfGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        appService = new AppService(appRepository, typeRepository, objectMapper, appMapper,pdfGenerator);
    }

    @Test
    void findAll() {
        List<App> apps = TestCreationFactory.listOf(App.class);
        when(appRepository.findAll()).thenReturn(apps);

        List<AppDto> all = appService.findAll();

        assertEquals(apps.size(), all.size());
    }

    @Test
    void importApps() throws Exception {
        List<AppDto> importedApps = appService.importApps();
        assertNotNull(importedApps);
    }

    @Test
    void findTypeByName() {
        long id = TestCreationFactory.randomLong();
        AppType appType = AppType.builder().id(id).name(GAME).build();
        when(typeRepository.findByName(GAME)).thenReturn(Optional.ofNullable(appType));

        AppType foundType = appService.findTypeByName("GAME");
        assertNotNull(appType);
        assertEquals(foundType.getName(), appType.getName());
    }

    @Test
    void create() {
        AppDto appDto = TestCreationFactory.newAppDto();
        App app = TestCreationFactory.newApp();
        AppType appType = AppType.builder().name(GAME).build();
        when(typeRepository.findByName(GAME)).thenReturn(Optional.ofNullable(appType));

        when(appMapper.toDto(app)).thenReturn(appDto);
        when(appRepository.save(app)).thenReturn(app);
        when(appMapper.fromDto(appDto)).thenReturn(app);

        assertEquals(appDto, appService.create(appDto));
    }

    @Test
    void update() {
        long id = TestCreationFactory.randomLong();
        AppDto appDto = TestCreationFactory.newAppDto();
        App app = TestCreationFactory.newApp();
        app.setId(id);
        AppType appType = AppType.builder().name(GAME).build();
        when(typeRepository.findByName(GAME)).thenReturn(Optional.ofNullable(appType));
        when(appRepository.findById(id)).thenReturn(Optional.of(app));
        when(appRepository.save(app)).thenReturn(app);
        when(appMapper.toDto(app)).thenReturn(appDto);

        assertEquals(appDto, appService.update(id, appDto));
    }

    @Test
    void findById() {
        long id = TestCreationFactory.randomLong();
        App app = TestCreationFactory.newApp();
        when(appRepository.findById(id)).thenReturn(Optional.of(app));
        assertEquals(app, appService.findById(id));
    }

    @Test
    void delete() {
        long id = TestCreationFactory.randomLong();
        doNothing().when(appRepository).deleteById(id);
        appService.delete(id);
        assertTrue(true);
    }

    @Test
    void findAllFiltered() {
        String nameFilter = "name filter";
        AppFilterRequestDto filter = AppFilterRequestDto.builder()
                .name(nameFilter)
                .maxPrice(10f)
                .type("GAME")
                .build();
        List<AppDto> appDtos = (TestCreationFactory.listOf(AppDto.class));
        AppType appType = AppType.builder().name(GAME).build();
        when(typeRepository.findByName(GAME)).thenReturn(Optional.ofNullable(appType));
        when(appRepository.findAll(
                AppSpecifications.specificationsFromFilter(filter, appType)
        ).stream().map(app -> {
            AppDto appDto = appMapper.toDto(app);
            appMapper.populateTypeInDto(app, appDto);
            return appDto;
        }).collect(Collectors.toList())).thenReturn(appDtos);

        List<AppDto> result = appService.findAllFiltered(filter);

        assertEquals(0, result.size());
    }

    @Test
    void generatePDF() {
        long id = TestCreationFactory.randomLong();
        File file = new File("application.pdf");
        App app = TestCreationFactory.newApp();
        when(appRepository.findById(id)).thenReturn(Optional.ofNullable(app));
        when(pdfGenerator.exportPDF(app)).thenReturn(file);
        assertEquals(appService.generatePDF(id), file);
    }

    @Test
    void callAppApiDetails() throws JsonProcessingException {
        ApiCallAppDto apiCallAppDto = new ApiCallAppDto();
        apiCallAppDto.setId(1841630L);
        appService.callAppDetailsApi(apiCallAppDto);
    }

}
