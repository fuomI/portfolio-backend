package fi.jonij.portfoliobackend.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = ProjectControllerJpa.class)
@AutoConfigureMockMvc(addFilters = false) // disable Spring Security for Unit tests
class ProjectControllerJpaTest {

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private ProjectControllerJpa projectControllerJpa;

    @Autowired
    private MockMvc mockMvc;

    public static final String ALL_PROJECTS_URL = "http://localhost:8080/list-projects";

    @Test
    void showListAllProjectsPage_basicScenario() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(ALL_PROJECTS_URL);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void showListAllProjectsPage_negativeScenario() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/list-projectz");

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(404, mvcResult.getResponse().getStatus());
    }




}
