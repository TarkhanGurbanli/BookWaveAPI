package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.comment.CommentDTO;
import com.tarkhan.backend.model.comment.CreateCommentDTO;
import com.tarkhan.backend.model.comment.UpdateCommentDTO;
import com.tarkhan.backend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "Comment Management",
        description = "APIs for managing comments in the system")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    @Operation(summary = "Get all comments",
            description = "Retrieves a list of all comments.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of comments retrieved successfully")
    })
    public ResponseEntity<List<CommentDTO>> getComments(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token
    ) {
        List<CommentDTO> commentDTOS = commentService.getComments(token);
        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get comment by ID",
            description = "Retrieves a comment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<CommentDTO> getCommentById(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the comment to retrieve") @PathVariable Long id
    ) {
        CommentDTO commentDTO = commentService.getComment(token, id);
        return ResponseEntity.ok(commentDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new comment", description = "Creates a new comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ResponseModel> createComment(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @RequestBody @Valid CreateCommentDTO request
    ) {
        commentService.addComment(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing comment", description = "Updates a comment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ResponseModel> updateComment(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the comment to update") @PathVariable @Valid Long id,
            @RequestBody @Valid UpdateCommentDTO request
    ) {
        commentService.updateComment(token, id, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a comment", description = "Deletes a comment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<ResponseModel> deleteComment(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the comment to delete") @PathVariable @Valid Long id
    ) {
        commentService.deleteComment(token, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}
