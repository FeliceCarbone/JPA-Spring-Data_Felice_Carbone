package it.aulab.progetto_blog.controllers;

import it.aulab.progetto_blog.repositories.CommentRepository;
import it.aulab.progetto_blog.repositories.PostRepository;
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


@RestController
@RequestMapping("/posts")
public class PostController {
    
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable("id") Long id) {
        return postRepository.findById(id).get(); 
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post post){
        post.setId(id);
        return postRepository.save(post);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") Long id){
        if (postRepository.existsById(id)){
            Post post = postRepository.findById(id).get();
            List<Comment> commentPosts = post.getComments();
            for (Comment comment : commentPosts) {
                commentRepository.delete(comment);
            }
            postRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post not found");
        }
    }
}
