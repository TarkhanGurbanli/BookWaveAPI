package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.author.CreateAuthorDTO;
import com.tarkhan.backend.model.author.UpdateAuthorDTO;
import com.tarkhan.backend.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Validated
@Tag(name = "Author Management")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new author")
    public ResponseEntity<ResponseModel> createAuthor(
            @Valid @ModelAttribute CreateAuthorDTO createAuthorDTO,
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        authorService.createAuthor(createAuthorDTO, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing author")
    public ResponseEntity<Void> updateAuthor(
            @Valid @PathVariable Long id,
            @Valid @ModelAttribute UpdateAuthorDTO updateAuthorDTO,
            @Valid @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        authorService.updateAuthor(id, updateAuthorDTO, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author")
    public ResponseEntity<Void> deleteAuthor(
            @Valid @PathVariable Long id
    ) throws IOException {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all authors")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }
}