package fi.jonij.portfoliobackend.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class ProjectControllerJpa {

    private ProjectRepository projectRepository;

    public ProjectControllerJpa(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
}
