package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.comment.CommentDTO;
import com.tarkhan.backend.model.comment.CreateCommentDTO;
import com.tarkhan.backend.model.comment.UpdateCommentDTO;
import com.tarkhan.backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments(
            @RequestHeader("Authorization") String token
    ) {
        List<CommentDTO> commentDTOS = commentService.getComments(token);
        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    )
    {
        CommentDTO commentDTO = commentService.getComment(token, id);
        return ResponseEntity.ok(commentDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseModel> createComment(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid CreateCommentDTO request
    ){
        commentService.addComment(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel> updateComment(
            @RequestHeader("Authorization") String token,
            @PathVariable @Valid Long id,
            @RequestBody @Valid UpdateCommentDTO request
    ){
        commentService.updateComment(token, id, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deleteComment(
            @RequestHeader("Authorization") String token,
            @PathVariable @Valid Long id
    ){
        commentService.deleteComment(token, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}
