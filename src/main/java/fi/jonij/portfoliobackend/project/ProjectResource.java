package fi.jonij.portfoliobackend.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectResource {

    private ProjectRepository projectRepository;

    public ProjectResource(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @RequestMapping("/rest/projects")
    public List<Project> retrieveAllProjects() {
        List<Project> projects = projectRepository.findAll();

        if(projects.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return projects;
    }

    @RequestMapping("/rest/projects/{projectId}")
    public Project retrieveProjectById(@PathVariable int projectId) {
        Optional<Project> project = projectRepository.findById(projectId);

        if(project.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return project.get();
    }
}
