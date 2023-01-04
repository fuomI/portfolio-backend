package fi.jonij.portfoliobackend.project;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import fi.jonij.portfoliobackend.aws.S3BucketService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("name")
public class ProjectControllerJpa {

    private final ProjectRepository projectRepository;
    private final S3BucketService s3BucketService;

    public ProjectControllerJpa(ProjectRepository projectRepository, S3BucketService s3BucketService) {
        this.projectRepository = projectRepository;
        this.s3BucketService = s3BucketService;
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
        Project project = new Project(username, "", "","", null,
                "", "", "" );
        model.addAttribute("project", project);

        return "project";
    }

    // Handling the form POST at 'add-project' route
    @RequestMapping(value="add-project", method = RequestMethod.POST)
    public String addNewProject(@RequestParam("file") MultipartFile file,
                                @Valid Project newProject, BindingResult result) {
        if(result.hasErrors()) {
            return "project";
        }

        String username = getLoggedInUserName();
        newProject.setUsername(username);

        // If no file to upload is chosen AmazonS3Exception is thrown
        try {
            s3BucketService.uploadProjectImage(file);
            newProject.setProjectImageFilename(file.getOriginalFilename());

        } catch (AmazonS3Exception e) {
            newProject.setProjectImageFilename("default.png");
            System.out.println("File upload failed: " + e.getMessage() + "\n" +
                    "Setting image to 'default.png'");
        }

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

    // Update route with POST method to actually update Projects
    @RequestMapping(value="update-project", method = RequestMethod.POST)
    public String updateProject(@RequestParam("file") MultipartFile file,
                                @Valid Project project, BindingResult result) {
        if(result.hasErrors()) {
            return "project";
        }

        String username = getLoggedInUserName();
        project.setUsername(username);

        // If no file to upload is chosen AmazonS3Exception is thrown
        try {
            s3BucketService.uploadProjectImage(file);
            project.setProjectImageFilename(file.getOriginalFilename());

        } catch (AmazonS3Exception e) {
            if(project.getProjectImageFilename().equals("")) {
                project.setProjectImageFilename("default.png");
                System.out.println("File upload failed: " + e.getMessage() + "\n" +
                                    "Setting image to 'default.png'");
            }
            // File already in storage, update projects projectImageFilename
            project.setProjectImageFilename(file.getOriginalFilename());
        }

        projectRepository.save(project);

        return "redirect:list-projects";
    }

    private static String getLoggedInUserName() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }
}
