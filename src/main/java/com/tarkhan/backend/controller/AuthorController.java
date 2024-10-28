package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.author.CreateAuthorDTO;
import com.tarkhan.backend.model.author.GetAuthorWithBooksDTO;
import com.tarkhan.backend.model.author.UpdateAuthorDTO;
import com.tarkhan.backend.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Author Management", description = "Endpoints for managing authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new author",
            description = "Creates a new author with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid author data")
    })
    public ResponseEntity<ResponseModel> create(@ModelAttribute CreateAuthorDTO dto) throws IOException {
        authorService.createAuthor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping(value = "/admin/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Update an existing author",
            description = "Updates the details of an existing author.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "400", description = "Invalid author data")
    })
    public ResponseEntity<ResponseModel> update(
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
    @Operation(summary = "Delete an author", description = "Deletes an author by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public ResponseEntity<ResponseModel> delete(@Valid @PathVariable Long id) throws IOException {
        authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }

    @GetMapping
    @Operation(summary = "Get all authors", description = "Retrieves a list of all authors.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of authors retrieved successfully")
    })
    public ResponseEntity<List<AuthorDTO>> getAll() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/with-books")
    @Operation(summary = "Get all authors with books",
            description = "Retrieves a list of authors along with their books.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of authors with books retrieved successfully")
    })
    public ResponseEntity<List<GetAuthorWithBooksDTO>> getAllAuthorsWithBooks() {
        List<GetAuthorWithBooksDTO> authors = authorService.getAllAuthorsWithBooks();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{authorId}/books")
    @Operation(summary = "Get a specific author with books by ID",
            description = "Retrieves a specific author along with their books using the author's ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author with books retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public ResponseEntity<GetAuthorWithBooksDTO> getAuthorByBooks(@Valid @PathVariable Long authorId) {
        GetAuthorWithBooksDTO author = authorService.getAuthorBooksById(authorId);
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific author by ID",
            description = "Retrieves a specific author using the author's ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public ResponseEntity<AuthorDTO> getById(@Valid @PathVariable("id") Long id) {
        AuthorDTO authorDTO = authorService.getByIdAuthor(id);
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @GetMapping("/page")
    @Operation(summary = "Get authors by pagination",
            description = "Retrieves a paginated list of authors.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Paginated list of authors retrieved successfully")
    })
    public ResponseEntity<List<AuthorDTO>> getAuthorsByPage(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize
    ) {
        List<AuthorDTO> authors = authorService.getAuthorsByPage(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }
}
