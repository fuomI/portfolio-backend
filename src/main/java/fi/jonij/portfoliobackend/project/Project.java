package fi.jonij.portfoliobackend.project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String projectName;
    private String projectType;
    private LocalDate dateOfCompletion;
    private boolean isDeployed;
    private String sourceCodeURL;
    private String projectURL;
    private String projectImageFilename;

}
