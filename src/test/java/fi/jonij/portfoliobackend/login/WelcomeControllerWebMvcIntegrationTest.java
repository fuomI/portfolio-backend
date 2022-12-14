package fi.jonij.portfoliobackend.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = WelcomeController.class)
class WelcomeControllerWebMvcIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    public static final String DEFAULT_URL = "http://localhost:8080/";

    // "tester" is a valid user and thus request should succeed with status code 200
    @WithMockUser(value = "tester")
    @Test
    public void requestToDefaultRoute_validUserScenario() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(DEFAULT_URL)
                .accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    // Without MockUser "tester" we should get status code 401 (Unauthorized)
    @Test
    public void requestToDefaultRoute_invalidUserScenario() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(DEFAULT_URL)
                .accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(401,mvcResult.getResponse().getStatus());
    }

}