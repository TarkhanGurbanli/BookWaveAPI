package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Blog;
import com.tarkhan.backend.entity.Image;
import com.tarkhan.backend.entity.User;
import com.tarkhan.backend.entity.enums.ImageType;
import com.tarkhan.backend.exception.BookWaveApiException;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.auth.JWTModel;
import com.tarkhan.backend.model.blog.BlogDTO;
import com.tarkhan.backend.model.blog.CreateBlogDTO;
import com.tarkhan.backend.model.blog.UpdateBlogDTO;
import com.tarkhan.backend.model.comment.CommentDTO;
import com.tarkhan.backend.repository.BlogRepository;
import com.tarkhan.backend.repository.CommentRepository;
import com.tarkhan.backend.repository.UserRepository;
import com.tarkhan.backend.service.BlogService;
import com.tarkhan.backend.service.auth.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final ImageServiceImpl imageService;

    @Override
    public void createBlog(String token, CreateBlogDTO request) throws Exception {
        JWTModel jwtModel = jwtUtil.decodeToken(token);
        Long userId = jwtModel.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        Blog blog = modelMapper.map(request, Blog.class);
        blog.setUser(user);

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            List<Long> imageIds = new ArrayList<>();

            for (MultipartFile imageFile : request.getImages()) {
                File tempFile = File.createTempFile("blog-", imageFile.getOriginalFilename());
                try {
                    imageFile.transferTo(tempFile);

                    Image blogImage = imageService.uploadImageToDrive(tempFile, ImageType.BLOG);
                    imageIds.add(blogImage.getId());

                } finally {
                    if (tempFile.exists()) {
                        tempFile.delete();
                    }
                }
            }

            blog.setImageIds(imageIds);
        }

        blogRepository.save(blog);
    }

    @Override
    public void updateBlog(String token, Long id, UpdateBlogDTO request) throws Exception {
        JWTModel jwtModel = jwtUtil.decodeToken(token);
        Long userId = jwtModel.getUserId();

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "ID", id));

        if (!blog.getUser().getId().equals(userId)) {
            throw new BookWaveApiException("You are not authorized to update this blog.");
        }

        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());

        List<Long> imageIds = new ArrayList<>();

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            for (MultipartFile imageFile : request.getImages()) {
                File tempFile = null;
                try {
                    tempFile = File.createTempFile("blog-", imageFile.getOriginalFilename());
                    imageFile.transferTo(tempFile);

                    Image blogImage = imageService.uploadImageToDrive(tempFile, ImageType.BLOG);
                    imageIds.add(blogImage.getId());

                } catch (IOException e) {
                    throw new RuntimeException("An error occurred while uploading the image: " + e.getMessage());
                } finally {
                    if (tempFile != null && tempFile.exists()) {
                        tempFile.delete();
                    }
                }
            }
        }

        blog.setImageIds(imageIds);
        blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(String token, Long id) throws Exception {
        JWTModel jwtModel = jwtUtil.decodeToken(token);
        Long userId = jwtModel.getUserId();

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "ID", id));

        if (!blog.getUser().getId().equals(userId)) {
            throw new BookWaveApiException("You are not authorized to delete this blog.");
        }

        blogRepository.delete(blog);
    }

    @Override
    public BlogDTO getBlog(String token, Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "ID", id));

        return modelMapper.map(blog, BlogDTO.class);
    }

    @Override
    public List<BlogDTO> getBlogs(String token) throws Exception {
        JWTModel jwtModel = jwtUtil.decodeToken(token);
        Long userId = jwtModel.getUserId();

        List<Blog> blogs = blogRepository.findByUserId(userId);

        return blogs.stream()
                .map(blog -> modelMapper.map(blog, BlogDTO.class))
                .toList();
    }

    @Override
    public BlogDTO getBlogWithComments(String token, Long id) throws Exception {
        JWTModel jwtModel = jwtUtil.decodeToken(token);
        Long userId = jwtModel.getUserId();

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "ID", id));

        BlogDTO blogDTO = modelMapper.map(blog, BlogDTO.class);

        List<CommentDTO> comments = commentRepository.findByBlogId(blog.getId()).stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .toList();

        blogDTO.setComments(comments);

        return blogDTO;
    }
}
