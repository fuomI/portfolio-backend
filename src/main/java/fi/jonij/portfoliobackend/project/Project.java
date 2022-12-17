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
    @NotNull
    private int id;
    @NotEmpty
    private String username;
    @Size(min=2, message="Enter at least 2 characters")
    @Size(max=30, message="Maximum allowed characters is 30")
    private String projectName;
    @Size(min=2, message="Enter at least 2 characters")
    @Size(max=15, message="Maximum allowed characters is 15")
    @Pattern(regexp="^[a-zA-z]+$", message="Enter only letters")
    private String projectType;
    @NotNull
    private LocalDate dateOfCompletion;
    @Pattern(regexp="^((http)|(https))[:][\\/][\\/]+$", message="Enter valid url starting with: http:// or https://")
    private String sourceCodeUrl;
    @Pattern(regexp="^((http)|(https))[:][\\/][\\/]+$", message="Enter valid url starting with: http:// or https://")
    private String projectUrl;
    private String projectImageFilename;
    @Size(min=10, message="Enter at least 10 characters")
    @Size(max=255, message="Maximum allowed characters is 255")
    @Pattern(regexp="^[a-zA-z]+$", message="Enter only letters")
    private String projectDescription;

    public Project() {
    }

    public Project(String username, String projectName, String projectType,
                   String projectDescription, LocalDate dateOfCompletion, String sourceCodeUrl,
                   String projectUrl, String projectImageFilename) {
        this.username = username;
        this.projectName = projectName;
        this.projectType = projectType;
        this.projectDescription = projectDescription;
        this.dateOfCompletion = dateOfCompletion;
        this.sourceCodeUrl = sourceCodeUrl;
        this.projectUrl = projectUrl;
        this.projectImageFilename = projectImageFilename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public LocalDate getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(LocalDate dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public String getSourceCodeUrl() {
        return sourceCodeUrl;
    }

    public void setSourceCodeUrl(String sourceCodeURL) {
        this.sourceCodeUrl = sourceCodeURL;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectURL) {
        this.projectUrl = projectURL;
    }

    public String getProjectImageFilename() {
        return projectImageFilename;
    }

    public void setProjectImageFilename(String projectImageFilename) {
        this.projectImageFilename = projectImageFilename;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectType='" + projectType + '\'' +
                ", dateOfCompletion=" + dateOfCompletion +
                ", sourceCodeUrl='" + sourceCodeUrl + '\'' +
                ", projectUrl='" + projectUrl + '\'' +
                ", projectImageFilename='" + projectImageFilename + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                '}';
    }
}
