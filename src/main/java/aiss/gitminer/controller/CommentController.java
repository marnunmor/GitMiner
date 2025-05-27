package aiss.gitminer.controller;

import aiss.gitminer.exception.CommentNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/api/comments")

public class CommentController {    //TODO las funciones que requieren parametros pueden
                                    // cambiarse por @RequestMapping si se quiere solicitar un paremtro

    @Autowired
    CommentRepository repository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.repository = commentRepository;
    }

    // GET http://localhost:8080/gitminer/api/comments
    @GetMapping
    public List<Comment> getComments() {
        return repository.findAll();
    }

    // GET http://localhost:8080/gitminer/api/comments/id/{id}
    @GetMapping("/id/{id}")
    public Comment getComment(@PathVariable Long id) throws CommentNotFoundException {
        Optional<Comment> comment = repository.findById(id);
        if (!comment.isPresent()) {
            throw new CommentNotFoundException();
        }
        return comment.get();
    }


}
