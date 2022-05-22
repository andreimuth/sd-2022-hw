package com.example.demo.app;

import com.example.demo.app.dto.AppDto;
import com.example.demo.app.dto.AppFilterRequestDto;
import com.example.demo.app.model.App;
import com.example.demo.review.ReviewService;
import com.example.demo.review.dto.UIReviewDto;
import com.example.demo.BaseControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.UrlMapping.*;
import static com.example.demo.TestCreationFactory.listOf;
import static com.example.demo.TestCreationFactory.randomLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppControllerTest extends BaseControllerTest {

    @InjectMocks
    private AppController controller;
    @Mock
    private AppService appService;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new AppController(appService, reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allApps() throws Exception {
        List<AppDto> apps = listOf(App.class);
        when(appService.findAll()).thenReturn(apps);
        ResultActions response = mockMvc.perform(get(APP + FIND_ALL));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(apps));
    }

    @Test
    void create() throws Exception {
        AppDto reqApp = AppDto.builder().name("name").description("description")
                .type("GAME").price(10F).build();

        when(appService.create(reqApp)).thenReturn(reqApp);

        ResultActions result = performPostWithRequestBody(APP + ADD_APP, reqApp);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqApp));
    }

    @Test
    void update() throws Exception {
        final long id = randomLong();
        AppDto reqApp = AppDto.builder().name("name").description("description")
                .type("GAME").price(10F).build();

        when(appService.update(id, reqApp)).thenReturn(reqApp);

        ResultActions result = performPutWithRequestBodyAndPathVariables(APP + UPDATE_APP, reqApp, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqApp));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(appService).delete(id);

        ResultActions result = performDeleteWithPathVariable(APP + DELETE_APP, id);
        verify(appService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }

    @Test
    void reviewsForItem() throws Exception {
        long id = randomLong();
        List<UIReviewDto> reviewDtos = new ArrayList<>(listOf(UIReviewDto.class));

        when(reviewService.getReviewsForApp(id)).thenReturn(reviewDtos);

        ResultActions result = performGetWithPathVariable(APP + GET_REVIEWS, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reviewDtos));
    }

    @Test
    void download() throws Exception {
        long id = randomLong();
        File file = new File("application.pdf");
        when(appService.generatePDF(id)).thenReturn(file);
        ResultActions result = performGetWithPathVariable(APP + DOWNLOAD, id);
        result.andExpect(status().isOk());
    }

    @Test
    void filteredItems() throws Exception {
        String nameFilter = "name filter";
        AppFilterRequestDto filter = AppFilterRequestDto.builder()
                .name(nameFilter)
                .maxPrice(10f)
                .build();

        List<AppDto> apps = (listOf(AppDto.class));
        when(appService.findAllFiltered(filter)).thenReturn(apps);

        ResultActions result = performGetWithModelAttributeAndParams(APP + FILTERED, Pair.of("filter", filter));

        verify(appService, times(1)).findAllFiltered(filter);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(apps));
    }

    @Test
    void importGames() throws Exception {
        List<AppDto> apps = (listOf(AppDto.class));
        when(appService.importApps()).thenReturn(apps);
        ResultActions response = mockMvc.perform(get(APP + IMPORT_APPS));
        response.andExpect(status().isOk());
    }

}
