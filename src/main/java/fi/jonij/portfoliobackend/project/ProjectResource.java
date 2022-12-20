package fi.jonij.portfoliobackend.project;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectResource {

    private ProjectRepository projectRepository;

    public ProjectResource(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    
}
