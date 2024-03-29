package fi.jonij.portfoliobackend.project;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import fi.jonij.portfoliobackend.aws.S3BucketService;
import fi.jonij.portfoliobackend.user.User;
import fi.jonij.portfoliobackend.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerJpaTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private S3BucketService s3BucketService;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @Mock
    private MultipartFile file;
    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ProjectControllerJpa projectControllerJpa;

    @Test
    public void showListAllProjectsPage_basicScenario() {
        // Set up the security context
        SecurityContextHolder.setContext(securityContext);

        // Set up the authentication object
        when(authentication.getName()).thenReturn("testuser");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Set up mock behavior for the project repository
        List<Project> projects = Arrays.asList(
                new Project("testuser", "testProject", "Coding",
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

    @Test
    public void showNewProjectPage_basicScenario() {
        // Set up the security context
        SecurityContextHolder.setContext(securityContext);

        // Set up the authentication object
        when(authentication.getName()).thenReturn("testuser");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        Project project = new Project("testuser","","","",null,"","", "");

        // Set up a ModelMap instance and invoke the controller method
        ModelMap model = new ModelMap();
        model.addAttribute("project", project);

        String viewName = projectControllerJpa.showNewProjectPage(model);
        Project result = (Project) model.get("project");

        // Assert the expected behavior
        assertEquals("project", viewName);
        assertEquals(0, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("", result.getProjectType());
        assertEquals("", result.getProjectDescription());
        assertNull(result.getDateOfCompletion());
        assertEquals("", result.getSourceCodeUrl());
        assertEquals("", result.getProjectUrl());
        assertEquals("", result.getProjectImageFilename());
    }

    @Test
    public void addNewProject_basicScenario() {
        // Set up the security context
        SecurityContextHolder.setContext(securityContext);

        // Set up the authentication object
        when(authentication.getName()).thenReturn("testuser");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Set up mock behavior for the project repository
        Project newProject = new Project("testuser", "testProject", "Coding",
                "This is a test project", LocalDate.now(), "http://github.com",
                "https://railway.app/project", "testproject.jpg");

        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findByUsername("testuser")).thenReturn(users);

        when(projectRepository.save(newProject)).thenReturn(newProject);

        // Set up mock behavior for the storage service
        when(file.getOriginalFilename()).thenReturn("testproject.jpg");
        doNothing().when(s3BucketService).uploadProjectImage(file);

        // Create a BindingResult instance and invoke the controller method
        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = projectControllerJpa.addNewProject(file, newProject, bindingResult);

        // Assert the expected behavior
        assertEquals("redirect:list-projects", viewName);
        verify(projectRepository).save(newProject);
        verify(s3BucketService).uploadProjectImage(file);
        assertEquals(user, newProject.getUser());
    }

    @Test
    public void addNewProject_bindingErrorsScenario() {
        // Set up mock behavior for the project repository
        Project newProject = new Project("testuser", "t", "c",
                "short", null, "ithub.com",
                "ilway.app/project", "testproject.jpg");

        // Invoke the controller method
        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = projectControllerJpa.addNewProject(file, newProject, bindingResult);

        // Assert the expected behavior
        assertEquals("project", viewName);
        verify(projectRepository, never()).save(newProject);
        verify(s3BucketService, never()).uploadProjectImage(file);
    }

    @Test
    public void addNewProject_fileOriginalFilenameIsNullScenario() {
        // Set up the security context
        SecurityContextHolder.setContext(securityContext);

        // Set up the authentication object
        when(authentication.getName()).thenReturn("testuser");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Set up mock behavior for the project repository
        Project newProject = new Project("testuser", "testProject", "Coding",
                "This is a test project", LocalDate.now(), "http://github.com",
                "https://railway.app/project", "");

        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findByUsername("testuser")).thenReturn(users);

        when(projectRepository.save(newProject)).thenReturn(newProject);

        // Set up mock behavior for the storage service
        doThrow(new AmazonS3Exception("File is null")).when(s3BucketService).uploadProjectImage(file);

        // Invoke the controller method
        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = projectControllerJpa.addNewProject(file, newProject, bindingResult);

        // Assert the expected behavior
        assertEquals("redirect:list-projects", viewName);
        verify(projectRepository).save(newProject);
        verify(s3BucketService).uploadProjectImage(file);
        assertEquals("default.png", newProject.getProjectImageFilename());
        assertEquals(user, newProject.getUser());
    }

    @Test
    public void deleteProject_basicScenario() {
        // Set up test data
        Project project = new Project("testuser", "testProject", "Coding",
                "This is a test project", LocalDate.now(), "http://github.com",
                "https://railway.app/project", "");
        project.setId(1);

        User user = new User();
        user.setId(1);
        user.setUsername("testuser");

        project.assignUser(user);

        Optional<Project> projectOptional = Optional.of(project);

        when(projectRepository.findById(1).stream().findFirst()).thenReturn(projectOptional);

        // Call the deleteProject method and verify the result
        String viewName = projectControllerJpa.deleteProject(1);
        assertEquals("redirect:list-projects", viewName);

        // Verify that the deleteById method was called with the correct id parameter
        verify(projectRepository).findById(1);
        verify(projectRepository).deleteById(1);
    }

    @Test
    public void showUpdateProjectPage_basicScenario() {
        // Set up test data
        int id = 1;
        Project project = new Project("testuser", "testProject", "Coding",
                "This is a test project", LocalDate.now(), "http://github.com",
                "https://railway.app/project", "");
        project.setId(id);

        // Set up mock behavior
        when(projectRepository.findById(id)).thenReturn(Optional.of(project));

        // Create mock ModelMap
        ModelMap model = mock(ModelMap.class);

        // Invoke the method being tested
        String viewName = projectControllerJpa.showUpdateProjectPage(id, model);

        // Verify the results
        assertEquals("project", viewName);
        verify(model).addAttribute("project", project);
    }

    @Test
    public void updateProject_basicScenario() {
        // Set up the security context
        SecurityContextHolder.setContext(securityContext);

        // Set up the authentication object
        when(authentication.getName()).thenReturn("testuser");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Set up mock behavior for the project repository
        Project updatedProject = new Project("testuser", "testProject", "Coding",
                "This is a test project", LocalDate.now(), "http://github.com",
                "https://railway.app/project", "testproject.jpg");

        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findByUsername("testuser")).thenReturn(users);

        when(projectRepository.save(updatedProject)).thenReturn(updatedProject);

        // Set up mock behavior for the storage service
        when(file.getOriginalFilename()).thenReturn("testproject.jpg");
        doNothing().when(s3BucketService).uploadProjectImage(file);

        // Create a BindingResult instance and invoke the controller method
        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = projectControllerJpa.updateProject(file, updatedProject, bindingResult);

        // Assert the expected behavior
        assertEquals("redirect:list-projects", viewName);
        verify(projectRepository).save(updatedProject);
        verify(s3BucketService).uploadProjectImage(file);
        assertEquals(user, updatedProject.getUser());
    }

    @Test
    public void updateProject_bindingErrorsScenario() {
        // Set up mock behavior for the project repository
        Project updatedProject = new Project("testuser", "t", "c",
                "short", null, "ithub.com",
                "ilway.app/project", "testproject.jpg");

        // Invoke the controller method
        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = projectControllerJpa.updateProject(file, updatedProject, bindingResult);

        // Assert the expected behavior
        assertEquals("project", viewName);
        verify(projectRepository, never()).save(updatedProject);
        verify(s3BucketService, never()).uploadProjectImage(file);
    }

    @Test
    public void updateProject_fileOriginalFilenameIsNullScenario() {
        // Set up the security context
        SecurityContextHolder.setContext(securityContext);

        // Set up the authentication object
        when(authentication.getName()).thenReturn("testuser");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Set up mock behavior for the project repository
        Project updatedProject = new Project("testuser", "testProject", "Coding",
                "This is a test project", LocalDate.now(), "http://github.com",
                "https://railway.app/project", "");

        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findByUsername("testuser")).thenReturn(users);

        when(projectRepository.save(updatedProject)).thenReturn(updatedProject);

        // Set up mock behavior for the storage service
        doThrow(new AmazonS3Exception("File is null")).when(s3BucketService).uploadProjectImage(file);

        // Invoke the controller method
        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = projectControllerJpa.updateProject(file, updatedProject, bindingResult);

        // Assert the expected behavior
        assertEquals("redirect:list-projects", viewName);
        verify(projectRepository).save(updatedProject);
        verify(s3BucketService).uploadProjectImage(file);
        assertEquals("", updatedProject.getProjectImageFilename());
        assertEquals(user, updatedProject.getUser());
    }

}
