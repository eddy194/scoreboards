package com.williamhill.scoreboards.controller;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.williamhill.scoreboards.utils.TestUtils.readFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ScoreBoardsControllerTest {

    private static final String GET_SCORE_BY_ID = "/event/score/1";
    private static final String GET_SCORE_BY_ID_NOT_FOUND = "/event/score/0";
    private static final String GET_ALL_EVENTS = "/events";
    private static final String ADD_EVENT = "/event";
    private static final String MODIFY_EVENT_SCORE = "/event/score";

    private static WireMockClassRule wireMock;
    private static MockMvc mockMvcSecured;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void beforeAll() {
        wireMock = new WireMockClassRule(WireMockSpring.options().port(9090));
        wireMock.start();
    }

    @After
    public void afterAll() {
        wireMock.stop();
    }

    @BeforeEach
    public void setup() {
        mockMvcSecured = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEvents_success() throws Exception {
        var resultActions = mockMvcSecured.perform(get(GET_ALL_EVENTS));

        var result = resultActions.andReturn().getResponse();
        assertEquals(200, result.getStatus());
        assertEquals(readFromFile("responses/get-all-events.json"), result.getContentAsString());
    }

    @Test
    public void getScoreByEventId_success() throws Exception {
        var resultActions = mockMvcSecured.perform(get(GET_SCORE_BY_ID));

        var result = resultActions.andReturn().getResponse();
        assertEquals(200, result.getStatus());
        assertEquals(readFromFile("responses/get-event.json"), result.getContentAsString());
    }

    @Test
    public void getScoreByEventId_unsuccessfully() throws Exception {
        var resultActions = mockMvcSecured.perform(get(GET_SCORE_BY_ID_NOT_FOUND));

        var result = resultActions.andReturn().getResponse();
        assertEquals(404, result.getStatus());
    }
    
    @Test
    public void addEvent_success() throws Exception {
        var resultActions = mockMvcSecured.perform(post(ADD_EVENT).content(readFromFile("requests/add-event.json")).contentType(MediaType.APPLICATION_JSON));
        
        var result = resultActions.andReturn().getResponse();
        assertEquals(200, result.getStatus());
        assertEquals(readFromFile("responses/add-event.json"), result.getContentAsString());
    }

    @Test
    public void modifyEventScore_success() throws Exception {
        var resultActions = mockMvcSecured.perform(put(MODIFY_EVENT_SCORE).content(readFromFile("requests/modify-event.json")).contentType(MediaType.APPLICATION_JSON));

        var result = resultActions.andReturn().getResponse();
        assertEquals(200, result.getStatus());
        assertEquals("Update successful", result.getContentAsString());
    }

    @Test
    public void modifyEventScore_unsuccessful() throws Exception {
        var resultActions = mockMvcSecured.perform(put(MODIFY_EVENT_SCORE).content(readFromFile("requests/modify-event-bad-version.json")).contentType(MediaType.APPLICATION_JSON));

        var result = resultActions.andReturn().getResponse();
        assertEquals(406, result.getStatus());
    }
}
