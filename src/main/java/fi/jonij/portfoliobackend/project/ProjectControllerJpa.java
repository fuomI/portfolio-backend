package fi.jonij.portfoliobackend.project;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
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

    // Route for adding a Project
    @RequestMapping(value="add-project", method = RequestMethod.GET)
    public String showNewProjectPage(ModelMap model) {
        String username = getLoggedInUserName();
        Project project = new Project(username, "", "", LocalDate.now(),
                                        true, "", "", "default.png" );
        model.addAttribute("project", project);

        return "project";
    }

    private static String getLoggedInUserName() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }
}
