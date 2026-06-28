package it.aulab.progetto_blog.services;

import java.util.List;

import it.aulab.progetto_blog.dtos.CommentDto;
import it.aulab.progetto_blog.dtos.PostDto;
import it.aulab.progetto_blog.models.Comment;
import it.aulab.progetto_blog.models.Post;

public interface CommentService {
    List<CommentDto> readAll();
    CommentDto read(Long id);
    CommentDto create(Comment comment);
    CommentDto update(Long id, Comment comment);
    void delete(Long id);
}
