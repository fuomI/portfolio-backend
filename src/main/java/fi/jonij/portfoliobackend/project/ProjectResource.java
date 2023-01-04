package fi.jonij.portfoliobackend.project;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import fi.jonij.portfoliobackend.aws.S3BucketService;
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

    private final ProjectRepository projectRepository;
    private final S3BucketService s3BucketService;

    public ProjectResource(ProjectRepository projectRepository, S3BucketService s3BucketService) {
        this.projectRepository = projectRepository;
        this.s3BucketService = s3BucketService;
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
        Resource resource;

        try {
            resource = s3BucketService.getProjectImage(projectImageFilename);

        } catch (AmazonS3Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(resource.exists()) {
            // Set the content type of the response based on the file extension
            String contentType;
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
