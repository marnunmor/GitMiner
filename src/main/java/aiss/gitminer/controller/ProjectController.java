package aiss.gitminer.controller;

import aiss.gitminer.exception.ProjectNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/api/projects")

public class ProjectController {    //TODO las funciones que requieren parametros pueden
                                    // cambiarse por @RequestMapping si se quiere solicitar un paremtro

    @Autowired
    ProjectRepository repository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.repository = projectRepository;
    }

    //GET http://localhost:8080/gitminer/api/projects
    @GetMapping
    public List<Project> getAllProjects() {
        return repository.findAll();
    }

    //GET http://localhost:8080/gitminer/api/projects/id/{id}
    @GetMapping("/id/{id}")
    public Project getProjectById(@PathVariable Long id) throws ProjectNotFoundException {
        Optional<Project> project = repository.findById(id);
        if (!project.isPresent()) {
            throw new ProjectNotFoundException();
        }
        return project.get();
    }

    //POST http://localhost:8080/gitminer/id/api/projects
    @ResponseStatus(HttpStatus.CREATED) //201
    @PostMapping
    public Project createProject(@Valid @RequestBody Project project) {
        Project _project = repository.
                save( new Project(project.getName(),
                        project.getWebUrl(),
                        project.getCommits(),
                        project.getIssues())
        );
        return _project;
    }

    //POST http://localhost:8080/gitminer/api/projects/id/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/id/{id}")
    public void updateProject(@Valid @RequestBody Project updatedproject, @PathVariable Long id) {
       Optional<Project> projectData = repository.findById(id);

       Project _project = projectData.get();
       _project.setName(updatedproject.getName());
       _project.setWebUrl(updatedproject.getWebUrl());
       _project.setCommits(updatedproject.getCommits());
       _project.setIssues(updatedproject.getIssues());
       repository.save(_project);
    }

    //DELETE http:/localhost:8080/gitminer/api/projects/id/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/id/{id}")
    public void deleteProject(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }


}
