package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.author.CreateAuthorDTO;
import com.tarkhan.backend.model.author.GetAuthorWithBooksDTO;
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

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Validated
@Tag(name = "Author Management")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new author")
    public ResponseEntity<ResponseModel> createAuthor(
            @ModelAttribute CreateAuthorDTO dto
    ) throws IOException {
        authorService.createAuthor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping(value = "/admin/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Update an existing author")
    public ResponseEntity<ResponseModel> updateAuthor(
            @Valid @PathVariable Long id,
            @Valid @ModelAttribute UpdateAuthorDTO updateAuthorDTO
    ) throws IOException {
        authorService.updateAuthor(id, updateAuthorDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/admin/{id}")
    @Operation(summary = "Delete an author")
    public ResponseEntity<ResponseModel> deleteAuthor(
            @Valid @PathVariable Long id
    ) throws IOException {
        authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }

    @GetMapping
    @Operation(summary = "Get all authors")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/with-books")
    @Operation(summary = "Get all authors with books")
    public ResponseEntity<List<GetAuthorWithBooksDTO>> getAllAuthorsWithBooks() {
        List<GetAuthorWithBooksDTO> authors = authorService.getAllAuthorsWithBooks();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{authorId}/books")
    @Operation(summary = "Get a specific author with books by ID")
    public ResponseEntity<GetAuthorWithBooksDTO> getAuthorByBooks(@Valid @PathVariable Long authorId) {
        GetAuthorWithBooksDTO author = authorService.getAuthorBooksById(authorId);
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific author by ID")
    public ResponseEntity<AuthorDTO> getById(
            @Valid @PathVariable("id") Long id) {
        AuthorDTO authorDTO = authorService.getByIdAuthor(id);
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @GetMapping("/page")
    @Operation(summary = "Get authors by pagination")
    public ResponseEntity<List<AuthorDTO>> getAuthorsByPage(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize
    ) {
        List<AuthorDTO> authors = authorService.getAuthorsByPage(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }
}
