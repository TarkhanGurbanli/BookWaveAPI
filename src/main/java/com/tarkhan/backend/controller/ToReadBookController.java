package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.readBook.CreateReadBookDTO;
import com.tarkhan.backend.model.readBook.ReadBookDTO;
import com.tarkhan.backend.model.readBook.UpdateReadBookDTO;
import com.tarkhan.backend.service.ToReadBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/toReadBooks")
@RequiredArgsConstructor
@Validated
@Tag(name = "To-Read Book Management")
public class ToReadBookController {

    private final ToReadBookService toReadBookService;

    @GetMapping
    @Operation(summary = "Get all to-read books",
            description = "Retrieves a list of all books the user intends to read.")
    public ResponseEntity<List<ReadBookDTO>> readBooks(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token) {
        List<ReadBookDTO> readBookDTOS = toReadBookService.getToReadBooks(token);
        return ResponseEntity.ok(readBookDTOS);
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    @Operation(summary = "Get to-read books by page",
            description = "Retrieves a paginated list of books the user intends to read.")
    public ResponseEntity<List<ReadBookDTO>> readBooksByPage(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "Page number to retrieve") @RequestParam int pageNumber,
            @Parameter(description = "Number of items per page") @RequestParam int pageSize) {
        List<ReadBookDTO> readBookDTOS = toReadBookService.getToReadBooksByPage(token, pageNumber, pageSize);
        return ResponseEntity.ok(readBookDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get to-read book by ID",
            description = "Retrieves a to-read book by its ID.")
    public ResponseEntity<ReadBookDTO> readBooksById(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the to-read book to retrieve") @PathVariable Long id) {
        ReadBookDTO readBookDTO = toReadBookService.getToReadBook(token, id);
        return ResponseEntity.ok(readBookDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new to-read book",
            description = "Creates a new book entry in the to-read list.")
    public ResponseEntity<ResponseModel> createReadBook(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @RequestBody @Valid CreateReadBookDTO request) {
        toReadBookService.createToReadBook(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing to-read book", description = "Updates an existing book entry in the to-read list by its ID.")
    public ResponseEntity<ResponseModel> updateReadBook(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the to-read book to update") @PathVariable @Valid Long id,
            @RequestBody @Valid UpdateReadBookDTO request) {
        toReadBookService.updateToReadBook(token, id, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a to-read book", description = "Deletes a book entry from the to-read list by its ID.")
    public ResponseEntity<ResponseModel> deleteReadBook(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the to-read book to delete") @PathVariable @Valid Long id) {
        toReadBookService.deleteToReadBook(token, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}
