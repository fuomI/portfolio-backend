package fi.jonij.portfoliobackend.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ProjectRepositoryIntegrationTest {

    @Autowired
    private ProjectRepository projectRepository;

    private Project testProject = new Project("user", "testProject", "Coding", LocalDate.now(),
            true, "http://github.com", "https://railway.app/project",
            "testproject.jpg");

    @BeforeEach
    public void saveProjectToProjectRepository () {
        projectRepository.save(testProject);
    }

    @Test
    public void findSavedProjectByUsername_basicScenario() {
        Project expected = testProject;

        assertEquals(expected, projectRepository.findByUsername("user").get(0));
    }

}