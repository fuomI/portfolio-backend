package fi.jonij.portfoliobackend.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectResourceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    public static final String ALL_PROJECTS_URL = "http://localhost:8080/rest/projects";
    public static final String IMAGES_URL = "http://localhost:8080/rest/images/";
}
