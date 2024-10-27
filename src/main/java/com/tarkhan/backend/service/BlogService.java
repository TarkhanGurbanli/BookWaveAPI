package com.tarkhan.backend.service;

import com.tarkhan.backend.model.blog.BlogDTO;
import com.tarkhan.backend.model.blog.CreateBlogDTO;
import com.tarkhan.backend.model.blog.UpdateBlogDTO;

import java.util.List;

public interface BlogService {
    void createBlog(String token, CreateBlogDTO request) throws Exception;
    void updateBlog(String token, Long id, UpdateBlogDTO request) throws Exception;
    void deleteBlog(String token, Long id) throws Exception;
    BlogDTO getBlog(String token, Long id);
    List<BlogDTO> getBlogs(String token) throws Exception;
    BlogDTO getBlogWithComments(String token, Long id) throws Exception;
}
