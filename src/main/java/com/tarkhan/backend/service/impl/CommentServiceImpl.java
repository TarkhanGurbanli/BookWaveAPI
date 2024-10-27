package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Blog;
import com.tarkhan.backend.entity.Book;
import com.tarkhan.backend.entity.Comment;
import com.tarkhan.backend.entity.User;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.auth.JWTModel;
import com.tarkhan.backend.model.comment.CommentDTO;
import com.tarkhan.backend.model.comment.CreateCommentDTO;
import com.tarkhan.backend.model.comment.UpdateCommentDTO;
import com.tarkhan.backend.repository.BlogRepository;
import com.tarkhan.backend.repository.BookRepository;
import com.tarkhan.backend.repository.CommentRepository;
import com.tarkhan.backend.repository.UserRepository;
import com.tarkhan.backend.service.CommentService;
import com.tarkhan.backend.service.auth.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BlogRepository blogRepository;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    @Override
    public void addComment(String token, CreateCommentDTO request) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", userId)
            );

            Book book = bookRepository.findById(request.getBookId()).orElseThrow(
                    () -> new ResourceNotFoundException("Book", "ID", request.getBookId())
            );

            Blog blog = blogRepository.findById(request.getBlogId()).orElseThrow(
                    () -> new ResourceNotFoundException("Blog", "ID", request.getBlogId())
            );

            Comment comment = modelMapper.map(request, Comment.class);
            comment.setUser(user);
            comment.setBook(book);
            comment.setBlog(blog);
            comment.setCreatedAt(request.getCreatedAt() != null ? request.getCreatedAt() : LocalDateTime.now());

            commentRepository.save(comment);

        } catch (Exception e) {
            throw new RuntimeException("Failed to add comment: " + e.getMessage(), e);
        }
    }


    @Override
    public void updateComment(String token, Long id, UpdateCommentDTO request) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", userId)
            );

            Book book = bookRepository.findById(request.getBookId()).orElseThrow(
                    () -> new ResourceNotFoundException("Book", "ID", request.getBookId())
            );

            Blog blog = blogRepository.findById(request.getBlogId()).orElseThrow(
                    () -> new ResourceNotFoundException("Blog", "ID", request.getBlogId())
            );

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Comment", "ID", id));

            modelMapper.map(request, comment);
            comment.setUser(user);
            comment.setBook(book);
            comment.setBlog(blog);
            comment.setCreatedAt(request.getCreatedAt() != null ? request.getCreatedAt() : LocalDateTime.now());

            commentRepository.save(comment);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update comment: " + e.getMessage(), e);
        }
    }


    @Override
    public void deleteComment(String token, Long id) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Comment", "ID", id));

            if(!comment.getUser().getId().equals(userId)) {
                throw new ResourceNotFoundException("User", "ID", userId);
            }
            commentRepository.delete(comment);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update comment: " + e.getMessage(), e);
        }
    }

    @Override
    public CommentDTO getComment(String token, Long id) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Comment", "ID", id));

            if(!comment.getUser().getId().equals(userId)) {
                throw new ResourceNotFoundException("User", "ID", userId);
            }
            return modelMapper.map(comment, CommentDTO.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update comment: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CommentDTO> getComments(String token) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            List<Comment> comments = commentRepository.findAllByUser(user);

            return comments.stream()
                    .map(comment -> modelMapper.map(comment, CommentDTO.class))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve comments: " + e.getMessage(), e);
        }
    }
}
