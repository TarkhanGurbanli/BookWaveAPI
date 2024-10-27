package com.tarkhan.backend.repository;

import com.tarkhan.backend.entity.Comment;
import com.tarkhan.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUser(User user);
    List<Comment> findByBlogId(Long blogId);
}
