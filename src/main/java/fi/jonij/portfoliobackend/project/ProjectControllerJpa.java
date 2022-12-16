package fi.jonij.portfoliobackend.project;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes("name")
public class ProjectControllerJpa {

    private ProjectRepository projectRepository;

    public ProjectControllerJpa(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Route for listing all projects of the logged user
    @RequestMapping("list-projects")
    public String listAllProjects(ModelMap model) {
        String username = getLoggedInUserName();
        List<Project> projects = projectRepository.findByUsername(username);
        model.addAttribute("projects", projects);

        return "listProjects";
    }

    private static String getLoggedInUserName() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }
}
