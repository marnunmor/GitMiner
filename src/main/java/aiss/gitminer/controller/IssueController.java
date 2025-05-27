package aiss.gitminer.controller;

import aiss.gitminer.exception.IssueNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repositories.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/api/issues")

public class IssueController {  //TODO las funciones que requieren parametros pueden
                                // cambiarse por @RequestMapping si se quiere solicitar un paremtro

    @Autowired
    IssueRepository repository;

    @Autowired
    public IssueController(IssueRepository issueRepository) {
        this.repository = issueRepository;
    }

    //GET http://localhost:8080/gitminer/api/issues
    @GetMapping
    public List<Issue> getIssues() {
        return repository.findAll();
    }

    //GET http://localhost:8080/gitminer/api/issues/id/{id}
    @GetMapping("/id/{id}")
    public Issue getIssueById(@PathVariable Long id) throws IssueNotFoundException {
        Optional<Issue> issue = repository.findById(id);
        if (!issue.isPresent()) {
            throw new IssueNotFoundException();
        }

        return issue.get();
    }

    //GET http://localhost:8080/gitminer/api/issues/state/{state}
    @GetMapping("/state/{state}")
    public Issue getIssueByState(@PathVariable String state) throws IssueNotFoundException {
        Optional<Issue> issue = repository.findOneByState(state);
        if (!issue.isPresent()) {
            throw new IssueNotFoundException();
        }
        return issue.get();
    }

    //GET http://localhost:8080/gitminer/api/issues/id/{id}/comments
    @GetMapping("/id/{id}/comments")
    public List<Comment> getIssuesComments(@PathVariable Long id) throws IssueNotFoundException {
        Optional<Issue> issue = repository.findById(id);
        if (!issue.isPresent()) {
            throw new IssueNotFoundException();
        }
        return issue.get().getComments();
    }

    //GET http://localhost:8080/gitminer/api/issues/author_id/{id}
    @GetMapping("/author_id/{author_id}")
    public Optional<Issue> getIssuesAuthors(@PathVariable Long author_id) throws IssueNotFoundException {
        Optional<Issue> issue = repository.findByauthor_id(author_id);
        if (!issue.isPresent()) {
            throw new IssueNotFoundException();
        }
        return issue;
    }

}
