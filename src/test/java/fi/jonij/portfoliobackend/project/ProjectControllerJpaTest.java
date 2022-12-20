package fi.jonij.portfoliobackend.project;

import fi.jonij.portfoliobackend.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectControllerJpaTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private StorageService storageService;

    @Mock
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    @InjectMocks
    private ProjectControllerJpa projectControllerJpa;

    @BeforeEach
    public void setUpSecurityContextAndAuthenticationObject() {
        // Set up the security context
        SecurityContextHolder.setContext(securityContext);

        // Set up the authentication object
        when(authentication.getName()).thenReturn("testuser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    public void showListAllProjectsPage_basicScenario() {
        // Set up mock behavior for the project repository
        List<Project> projects = Arrays.asList(new Project("testuser", "testProject", "Coding",
                                "This is testProject for testing the project repository",
                                                LocalDate.now(), "http://github.com", "https://railway.app/project",
                                "testproject.jpg"),

                                                new Project("testuser", "testProject2", "Coding",
                                    "This is testProject for testing the project repository",
                                                    LocalDate.now(), "http://github.com", "https://railway.app/project",
                                    "testproject2.jpg"));

        when(projectRepository.findByUsername("testuser")).thenReturn(projects);

        // Create a ModelMap instance and invoke the controller method
        ModelMap model = new ModelMap();
        model.addAttribute("projects", projects);

        String viewName = projectControllerJpa.showListAllProjectsPage(model);

        // Assert the expected behavior
        assertEquals("listProjects", viewName);
        assertEquals(projects, model.get("projects"));
    }

}
