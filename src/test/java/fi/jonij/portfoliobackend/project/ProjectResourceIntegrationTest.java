package fi.jonij.portfoliobackend.project;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectResourceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    public static final String ALL_PROJECTS_URL = "http://localhost:8080/rest/projects";
    public static final String IMAGES_URL = "http://localhost:8080/rest/images";

    @Test
    public void retrieveAllProjects_successfulScenario() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        // GET request using TestRestTemplate
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(ALL_PROJECTS_URL, HttpMethod.GET, httpEntity, String.class);

        String expectedResponse = """
                                [
                                    {
                                        "id": 5,
                                        "username": "tester",
                                        "projectName": "test project one",
                                        "projectType": "Coding",
                                        "dateOfCompletion": "2022-12-20",
                                        "sourceCodeUrl": "https://www.github.com/testproj1",
                                        "projectUrl": "https://railway.app/testproj1",
                                        "projectImageFilename": "default.png",
                                        "projectDescription": "Project for testing the backend of my portfolio"
                                    },
                                    {
                                        "id": 6,
                                        "username": "tester",
                                        "projectName": "test project two",
                                        "projectType": "UX",
                                        "dateOfCompletion": "2022-12-20",
                                        "sourceCodeUrl": "https://www.github.com/testproj2",
                                        "projectUrl": "https://railway.app/testproj2",
                                        "projectImageFilename": "default.png",
                                        "projectDescription": "Second project for testing the backend of my portfolio"
                                    }
                                ]
                                """;

        System.out.println(responseEntity.getBody().toString());

        // Check status
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // Check content-type
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

        // Check content, strict: false
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    @Test
    public void retrieveProjectById_successfulScenario() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        // GET request using TestRestTemplate
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(ALL_PROJECTS_URL + "/5", HttpMethod.GET, httpEntity, String.class);

        String expectedResponse = """
                                    {
                                        "id": 5,
                                        "username": "tester",
                                        "projectName": "test project one",
                                        "projectType": "Coding",
                                        "dateOfCompletion": "2022-12-20",
                                        "sourceCodeUrl": "https://www.github.com/testproj1",
                                        "projectUrl": "https://railway.app/testproj1",
                                        "projectImageFilename": "default.png",
                                        "projectDescription": "Project for testing the backend of my portfolio"
                                    }
                                """;

        System.out.println(responseEntity.getBody().toString());

        // Check status
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // Check content-type
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

        // Check content, strict: false
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    @Test
    public void retrieveProjectImage_successfulScenario() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/png");

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        // GET request using TestRestTemplate
        ResponseEntity<Resource> responseEntity =
                restTemplate.exchange(IMAGES_URL + "/default.png", HttpMethod.GET, httpEntity, Resource.class);

        // Compare bytes of the response body and the file from filesystem
        try {
            String expectedFilePath = "src/main/resources/static/upload/default.png";
            byte[] expected = Files.readAllBytes(Paths.get(expectedFilePath));

            if(responseEntity.getBody() != null) {
                InputStream inputStream = responseEntity.getBody().getInputStream();
                byte[] actual = IOUtils.toByteArray(inputStream);

                // Check status
                assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

                // Check content-type
                assertEquals("image/png", responseEntity.getHeaders().get("Content-Type").get(0));

                // Check content
                assertArrayEquals(expected, actual);

            } else {
                fail("Response body was null!");
            }

        } catch (IOException e) {
            fail("IO operations failed, " + e.getMessage());
        }
    }
}