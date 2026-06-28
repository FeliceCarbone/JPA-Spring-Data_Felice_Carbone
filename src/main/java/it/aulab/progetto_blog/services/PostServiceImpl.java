package it.aulab.progetto_blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progetto_blog.dtos.AuthorDto;
import it.aulab.progetto_blog.dtos.PostDto;
import it.aulab.progetto_blog.models.Author;
import it.aulab.progetto_blog.models.Comment;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.AuthorRepository;
import it.aulab.progetto_blog.repositories.CommentRepository;
import it.aulab.progetto_blog.repositories.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<PostDto> readAll() {
        List<PostDto> dtos = new  ArrayList<PostDto>();
        for (Post post : postRepository.findAll()){
            dtos.add(mapper.map(post, PostDto.class));
        }
        return dtos;
    }

    @Override
    public PostDto read(Long id) {
        Optional<Post> optPost = postRepository.findById(id);
        if (optPost.isPresent()){
            return mapper.map(optPost.get(), PostDto.class);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post id= " + id +"not found");
        }
    }

    @Override
    public PostDto create(Post post) {
        if (post.getAuthor() == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return mapper.map(postRepository.save(post), PostDto.class);
    }

    @Override
    public PostDto update(Long id, Post post) {
        if (postRepository.existsById(id)){
        post.setId(id);
        return mapper.map(postRepository.save(post), PostDto.class);
    }else{
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    }

    @Override
    public void delete(Long id) {
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
