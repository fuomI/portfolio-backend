package fi.jonij.portfoliobackend.project;

import fi.jonij.portfoliobackend.storage.StorageException;
import fi.jonij.portfoliobackend.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectResource {

    private ProjectRepository projectRepository;

    private StorageService storageService;

    public ProjectResource(ProjectRepository projectRepository, StorageService storageService) {
        this.projectRepository = projectRepository;
        this.storageService = storageService;
    }

    //
    @GetMapping("/rest/projects")
    public List<Project> retrieveAllProjects() {
        List<Project> projects = projectRepository.findAll();

        if(projects.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return projects;
    }

    @GetMapping("/rest/projects/{projectId}")
    public Project retrieveProjectById(@PathVariable int projectId) {
        Optional<Project> project = projectRepository.findById(projectId);

        if(project.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return project.get();
    }

    @GetMapping("/rest/images/{projectImageFilename}")
    public ResponseEntity<Resource> retrieveProjectImage(@PathVariable String projectImageFilename) {
        Resource resource = null;

        try {
            resource = storageService.loadAsResource(projectImageFilename);

        } catch (StorageException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(resource.exists()) {
            // Set the content type of the response based on the file extension
            String contentType = null;
            if (projectImageFilename.endsWith(".png")) {
                contentType = MediaType.IMAGE_PNG_VALUE;
            } else if (projectImageFilename.endsWith(".jpg") || projectImageFilename.endsWith(".jpeg")) {
                contentType = MediaType.IMAGE_JPEG_VALUE;
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
        }

        return ResponseEntity.notFound().build();
    }

}
