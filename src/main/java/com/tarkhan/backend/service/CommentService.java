package com.tarkhan.backend.service;

import com.tarkhan.backend.model.comment.CommentDTO;
import com.tarkhan.backend.model.comment.CreateCommentDTO;
import com.tarkhan.backend.model.comment.UpdateCommentDTO;

import java.util.List;

public interface CommentService {
    void addComment(String token, CreateCommentDTO request);
    void updateComment(String token, Long id, UpdateCommentDTO request);
    void deleteComment(String token, Long id);
    CommentDTO getComment(String token, Long id);
    List<CommentDTO> getComments(String token);
}
