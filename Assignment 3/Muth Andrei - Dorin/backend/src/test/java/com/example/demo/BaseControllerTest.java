package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public abstract class BaseControllerTest {
    protected MockMvc mockMvc;

    @BeforeEach
    protected void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    protected ResultMatcher jsonContentToBe(Object expectedJsonContent) throws JsonProcessingException {
        return content().json(new ObjectMapper().writeValueAsString(expectedJsonContent), true);
    }

    protected ResultActions performPostWithRequestBody(String path, Object body) throws Exception {
        return mockMvc.perform(post(path)
                .content(asJsonString(body))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));
    }

    protected ResultActions performPatchWithRequestBody(String path, Object body) throws Exception {
        return mockMvc.perform(patch(path)
                .content(asJsonString(body))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));
    }

    protected ResultActions performPutWithRequestBody(String path, Object body) throws Exception {
        return mockMvc.perform(put(path)
                .content(asJsonString(body))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));
    }

    protected ResultActions performGet(String path) throws Exception {
        return mockMvc.perform(get(path));
    }

    protected ResultActions performPutWithRequestBodyAndPathVariables(String path, Object body, Object... pathVariables) throws Exception {
        return mockMvc.perform(put(path, pathVariables)
                .content(asJsonString(body))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));
    }

    protected String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ResultActions performDeleteWithPathVariable(String path, Object pathVariable) throws Exception {
        return mockMvc.perform(jsonType(delete(path, pathVariable)));
    }

    protected ResultActions performGetWithPathVariable(String path, Object pathVariable) throws Exception {
        return mockMvc.perform(jsonType(get(path, pathVariable)));
    }

    protected ResultActions performGetWithModelAttributeAndParams(String path, Pair<String, Object> modelAttribute) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(path);
        //params.forEach(param -> requestBuilder.param(param.getFirst(), param.getSecond()));
        requestBuilder.flashAttr(modelAttribute.getFirst(), modelAttribute.getSecond());
        return mockMvc.perform(requestBuilder);
    }

    private RequestBuilder jsonType(MockHttpServletRequestBuilder content) {
        return content.contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);
    }


}