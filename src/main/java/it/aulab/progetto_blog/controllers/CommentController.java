package it.aulab.progetto_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progetto_blog.models.Author;
import it.aulab.progetto_blog.models.Comment;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.CommentRepository;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable("id") Long id) {
        return commentRepository.findById(id).get(); 
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    @PutMapping("{id}")
    public Comment updateComment(@PathVariable("id") Long id, @RequestBody Comment comment){
        comment.setId(id);
        return commentRepository.save(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }
}
