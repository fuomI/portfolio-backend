package fi.jonij.portfoliobackend.project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
public class Project {

    @Id
    @GeneratedValue
    @NotEmpty
    private int id;
    @NotEmpty
    private String username;
    @NotEmpty
    @Size(min=2, message="Enter at least 2 characters")
    private String projectName;
    @NotEmpty
    @Size(min=2, message="Enter at least 2 characters")
    @Pattern(regexp="^[a-zA-z]+$", message="Enter only letters")
    private String projectType;
    @NotEmpty
    @Past
    private LocalDate dateOfCompletion;
    @NotNull
    private boolean isDeployed;
    @Pattern(regexp="((http|https)://)?")
    private String sourceCodeURL;
    @Pattern(regexp="((http|https)://)?")
    private String projectURL;
    @Pattern(regexp="(.jpg|.jpeg|.png)$")
    private String projectImageFilename;

    public Project() {
    }

    public Project(int id, String username, String projectName, String projectType,
                   LocalDate dateOfCompletion, boolean isDeployed, String sourceCodeURL,
                   String projectURL, String projectImageFilename) {
        this.id = id;
        this.username = username;
        this.projectName = projectName;
        this.projectType = projectType;
        this.dateOfCompletion = dateOfCompletion;
        this.isDeployed = isDeployed;
        this.sourceCodeURL = sourceCodeURL;
        this.projectURL = projectURL;
        this.projectImageFilename = projectImageFilename;
    }

    
}
