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
    @NotEmpty
    @Size(min=2, message="Enter at least 2 characters")
    @Size(max=30, message="Maximum allowed characters is 30")
    private String projectName;
    @NotEmpty
    @Size(min=2, message="Enter at least 2 characters")
    @Size(max=15, message="Maximum allowed characters is 15")
    @Pattern(regexp="^[a-zA-z]+$", message="Enter only letters")
    private String projectType;
    @NotNull
    private LocalDate dateOfCompletion;
    @NotNull
    private boolean deployed;
    @Pattern(regexp="^((http)|(https))[:][\\/][\\/]+$", message="Enter valid url starting with: http:// or https://")
    private String sourceCodeUrl;
    @Pattern(regexp="^((http)|(https))[:][\\/][\\/]+$", message="Enter valid url starting with: http:// or https://")
    private String projectUrl;
    private String projectImageFilename;

    public Project() {
    }

    public Project(String username, String projectName, String projectType,
                   LocalDate dateOfCompletion, boolean deployed, String sourceCodeUrl,
                   String projectUrl, String projectImageFilename) {
        this.username = username;
        this.projectName = projectName;
        this.projectType = projectType;
        this.dateOfCompletion = dateOfCompletion;
        this.deployed = deployed;
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

    public LocalDate getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(LocalDate dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public boolean isDeployed() {
        return deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
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
                ", isDeployed=" + deployed +
                ", sourceCodeURL='" + sourceCodeUrl + '\'' +
                ", projectURL='" + projectUrl + '\'' +
                ", projectImageFilename='" + projectImageFilename + '\'' +
                '}';
    }
}
