package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.blog.BlogDTO;
import com.tarkhan.backend.model.blog.CreateBlogDTO;
import com.tarkhan.backend.model.blog.UpdateBlogDTO;
import com.tarkhan.backend.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
@Validated
public class BlogController {

    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs(
            @RequestHeader("Authorization") String token
    ) throws Exception {
        List<BlogDTO> blogDTOS = blogService.getBlogs(token);
        return ResponseEntity.ok(blogDTOS);
    }

//    @GetMapping("/{pageNumber}/{pageSize}")
//    public ResponseEntity<List<BlogDTO>> getBlogsByPage(
//            @RequestHeader("Authorization") String token,
//            @PathVariable int pageNumber,
//            @PathVariable int pageSize
//    ) {
//        List<BlogDTO> blogDTOS = blogService.getBlogsByPage(token, pageNumber, pageSize);
//        return ResponseEntity.ok(blogDTOS);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlogById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) {
        BlogDTO blogDTO = blogService.getBlog(token, id);
        return ResponseEntity.ok(blogDTO);
    }

    @PostMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseModel> createBlog(
            @RequestHeader("Authorization") String token,
            @ModelAttribute @Valid CreateBlogDTO createBlogDTO
    ) throws Exception {
        blogService.createBlog(token, createBlogDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping(value = "/user/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseModel> updateBlog(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @ModelAttribute @Valid UpdateBlogDTO updateBlogDTO
    ) throws Exception {
        blogService.updateBlog(token, id, updateBlogDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ResponseModel> deleteBlog(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) throws Exception {
        blogService.deleteBlog(token, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<BlogDTO> getBlogWithComments(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) throws Exception {
        BlogDTO blogDTO = blogService.getBlogWithComments(token, id);
        return ResponseEntity.ok(blogDTO);
    }
}
