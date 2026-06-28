package it.aulab.progetto_blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progetto_blog.dtos.CommentDto;
import it.aulab.progetto_blog.dtos.PostDto;
import it.aulab.progetto_blog.models.Comment;
import it.aulab.progetto_blog.models.Post;
import it.aulab.progetto_blog.repositories.AuthorRepository;
import it.aulab.progetto_blog.repositories.CommentRepository;
import it.aulab.progetto_blog.repositories.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper mapper;

     @Override
    public List<CommentDto> readAll() {
        List<CommentDto> dtos = new  ArrayList<CommentDto>();
        for (Comment comment : commentRepository.findAll()){
            dtos.add(mapper.map(comment, CommentDto.class));
        }
        return dtos;
    }

    @Override
    public CommentDto read(Long id) {
        Optional<Comment> optComment = commentRepository.findById(id);
        if (optComment.isPresent()){
            return mapper.map(optComment.get(), CommentDto.class);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment id= " + id +"not found");
        }
    }

    @Override
    public CommentDto create(Comment comment) {
        if (comment.getBody() == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return mapper.map(commentRepository.save(comment), CommentDto.class);
    }

    @Override
    public CommentDto update(Long id, Comment comment) {
        if (commentRepository.existsById(id)){
        comment.setId(id);
        return mapper.map(commentRepository.save(comment), CommentDto.class);
    }else{
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    }

    @Override
    public void delete(Long id) {
       if (commentRepository.existsById(id)){
            commentRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Comment not found");
        }
    }
}
