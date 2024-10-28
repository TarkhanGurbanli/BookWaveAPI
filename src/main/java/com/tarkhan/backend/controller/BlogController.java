package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.blog.BlogDTO;
import com.tarkhan.backend.model.blog.CreateBlogDTO;
import com.tarkhan.backend.model.blog.UpdateBlogDTO;
import com.tarkhan.backend.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Blog Management", description = "Endpoints for managing blogs")
@Validated
public class BlogController {

    private final BlogService blogService;

    @GetMapping
    @Operation(summary = "Get all blogs", description = "Retrieves a list of all blogs.")
    public ResponseEntity<List<BlogDTO>> getAllBlogs(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token
    ) throws Exception {
        List<BlogDTO> blogDTOS = blogService.getBlogs(token);
        return ResponseEntity.ok(blogDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get blog by ID", description = "Retrieves a blog by its ID.")
    public ResponseEntity<BlogDTO> getBlogById(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the blog") @PathVariable Long id
    ) {
        BlogDTO blogDTO = blogService.getBlog(token, id);
        return ResponseEntity.ok(blogDTO);
    }

    @PostMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new blog", description = "Allows a user to create a new blog.")
    public ResponseEntity<ResponseModel> create(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
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
    @Operation(summary = "Update an existing blog",
            description = "Allows a user to update an existing blog by ID.")
    public ResponseEntity<ResponseModel> update(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the blog") @PathVariable Long id,
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
    @Operation(summary = "Delete a blog",
            description = "Allows a user to delete a blog by ID.")
    public ResponseEntity<ResponseModel> delete(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the blog") @PathVariable Long id
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
    @Operation(summary = "Get blog with comments",
            description = "Retrieves a blog along with its comments by blog ID.")
    public ResponseEntity<BlogDTO> getBlogWithComments(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the blog") @PathVariable Long id
    ) throws Exception {
        BlogDTO blogDTO = blogService.getBlogWithComments(token, id);
        return ResponseEntity.ok(blogDTO);
    }
}
