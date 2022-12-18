package fi.jonij.portfoliobackend.project;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String showListAllProjectsPage(ModelMap model) {
        String username = getLoggedInUserName();
        List<Project> projects = projectRepository.findByUsername(username);
        model.addAttribute("projects", projects);

        return "listProjects";
    }

    // Route for showing the 'Add Project' -page
    @RequestMapping(value="add-project", method = RequestMethod.GET)
    public String showNewProjectPage(ModelMap model) {
        String username = getLoggedInUserName();
        Project project = new Project(username, "", "","", LocalDate.now(),
                "", "", "" );
        model.addAttribute("project", project);

        return "project";
    }

    // Handling the form POST at 'add-project' route
    @RequestMapping(value="add-project", method = RequestMethod.POST)
    public String addNewProject(@Valid Project newProject, BindingResult result) {
        if(result.hasErrors()) {
            return "project";
        }

        String username = getLoggedInUserName();
        newProject.setUsername(username);
        newProject.setProjectImageFilename("default.png"); // Temporary hard code, until file upload implemented
        projectRepository.save(newProject);

        return "redirect:list-projects";
    }

    // Route for deleting a project by id
    @RequestMapping("delete-project")
    public String deleteProject(@RequestParam int id) {
        projectRepository.deleteById(id);

        return "redirect:list-projects";
    }

    // Route for showing project page with attributes of a specific Project (update)
    @RequestMapping(value="update-project", method = RequestMethod.GET)
    public String showUpdateProjectPage(@RequestParam int id, ModelMap model) {
        Project project = projectRepository.findById(id).get();
        model.addAttribute("project", project);

        return "project";
    }

    private static String getLoggedInUserName() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }
}
