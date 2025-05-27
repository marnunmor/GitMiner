package aiss.gitminer.controller;

import aiss.gitminer.exception.CommitNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.repositories.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/api/commits")

public class CommitController { //TODO las funciones que requieren parametros pueden
                                // cambiarse por @RequestMapping si se quiere solicitar un paremtro

    @Autowired
    CommitRepository repository;

    public CommitController(CommitRepository CommitRepository) {
        this.repository = CommitRepository;
    }

    //GET http://localhost:8080/gitminer/api/commits
    @GetMapping
    public List<Commit> getCommits() {
        return repository.findAll();
    }

    //GET http://localhost:8080/gitminer/api/commits/id/{id}
    @GetMapping("/id/{id}")
    public Commit getCommitByID(@PathVariable Long id) throws CommitNotFoundException {
        Optional<Commit> commit = repository.findById(id);
        if (!commit.isPresent()) {
            throw new CommitNotFoundException();
        }
        return commit.get();
    }

    //GET http://localhost:8080/gitminer/api/commits/email/{author_email}
    @GetMapping("/email/{author_email}")
    public Commit getCommitByAuthorEmail(@PathVariable String author_email) throws CommitNotFoundException {
        Optional<Commit> commit = repository.findOneByAuthorEmail(author_email);
        if (!commit.isPresent()) {
            throw new CommitNotFoundException();
        }
        return commit.get();
    }




}
