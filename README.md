# Portfolio backend (jonij.fi)
## Project description in a nutshell

This is the backend of a fullstack project, which is a portfolio for showcasing my past projects.

Frontend shall be done after the backend is ready.

## Git workflow

### The project has five different kinds of branches:
- **main (production)**
  - This is only touched when release is ready to be merged to the main.


- **release**
  - Last check that everything is as it should for production phase.


- **development**
  - Has development settings like H2 memory database etc.


- **project_task_3, 4, 5...**
  - When project_task is complete, it is then merged to the development branch.


- **coding_task_1, 2, 3...**
  - When coding_task is done, it is then merged to the development branch.
  - Last coding task before release is merged into release instead of development.


- **hotfix_1, 2, 3...**
  - Changes that are not planned but must be done are handled in hotfix branches.
  - Merging to development when hotfix is done.


## Requirements

- **Login page for admin**
  - If login successful &rarr; Land to Welcome -page
  - If not &rarr; Message: "Invalid credentials"


- **Admin can logout**


- **All relevant information stored about Projects**
  - **id** (int), Primary key, required
  - **username** (String), required
  - **projectName** (String), required
  - **projectType** (String), required
  - **projectDescription** (String), required
  - **dateOfCompletion** (LocalDate), required
  - **sourceCodeURL** (String), required
  - **projectURL** (String), required
  - **projectImageFilename** (String), required


- **Admin can interact with project database (MySQL) using graphical UI**
  - **Create project**
    - id is set automatically
    - Project image can be uploaded, projectImageFilename set in process
    - All the rest Project fields can be set
  - **Read project**
    - Projects -page listing all projects
  - **Update project**
    - Update by id
    - New project image can be uploaded, projectImageFilename set in process
    - All the rest Project fields can be updated
  - **Delete project**
    - Delete by id


- **Frontend can access Projects data via Internal REST API**


## Technologies

### Spring Boot
- **Spring Security** - Login functionality and authentication
- **Spring Web** - For building webapp
- **Spring Boot starter test** - JUnit, Mockito etc. for writing  tests
- **Spring Data JPA** - Easy database interactions
- **Spring Boot Rest** - REST API



### Other

- **Bootstrap** - CSS Framework for styling
- **JSTL** - JSP Standard Tag Library for working with JSPs
- **Apache Commons** - Working with files
- **MySQL drivers** - Production database
- **H2** - Memory database for development purposes
- **Jasypt** - Encrypting properties like datasource URL
- **Amazon S3** - Remote file storage


## Dataflow

![dataflow](documentation_images/dataflow.jpg)

## General project & coding -tasks

These are the project tasks, classes and features that I came up when planning this project.

### Trello

![tasks_1](documentation_images/tasks_1.png)

![tasks_2](documentation_images/tasks_2.png)

![tasks_3](documentation_images/tasks_3.png)


## Initial Project timetable - DEPRECATED

![project_timetable](documentation_images/project_timetable.png)

## Reasons for postponing release

### SpringBoot 3 & JSP

I ran into problems when I tried running my WAR using SpringBoot 3.0.
I tried debugging for a couple of days but in vain.
I even started a thread in [stack overflow](https://stackoverflow.com/questions/74913190/spring-boot-apps-jar-not-working-issue-with-tomcat-embed-jasper) when nothing seemed to be working.

**&rarr; Solution was to downgrade SpringBoot to version 2.7.6.**

### File storage and WAR

I initially had storage system that stored files into the applications own filesystem.
However, this feature broke when I tried it with the executable WAR.
Application seemed to be working otherwise.

**&rarr; My solution was to change to remote storage, S3 from Amazon to be specific.**

### Creating Docker image and deploying (WORK IN PROGRESS 10.1.2023)

I have successfully created the docker image and my application runs great locally.
However, if I wanted to use same Dockerfile when deploying to railway.app
I would probably need to upload my WAR to this github repository.
The WAR is 300 mb so this isn't allowed.

If I want to create image different way I probably need to upload everything to
github including sensitive data that has been .gitignored so far. The data would have
been vulnerable in the WAR also so fix is needed.

**&rarr; Solution which I'm going to try is to encrypt datasource URL and then store
sensitive data in the database, possibly encrypted. After that I try to create image by 
adding necessary steps to the Dockerfile.**

## Release 1.2 15.1.2023

### App is now deployed inside AWS EC2 virtual machine inside a docker container [here.](http://ec2-16-170-141-77.eu-north-1.compute.amazonaws.com:6565) However, it is protected by username and password. It is also quite slow at the moment, so I'm probably going to allocate more hardware resources to it.

