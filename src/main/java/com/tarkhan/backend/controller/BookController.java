package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.book.*;
import com.tarkhan.backend.service.BookService;
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
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Validated
@Tag(name = "Book Management")
public class BookController {

    private final BookService bookService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseModel> create(
            @Valid @ModelAttribute CreateBookDTO dto
            )throws IOException {
        bookService.createBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseModel> update(
            @Valid @PathVariable("id") Long id,
            @Valid @ModelAttribute UpdateBookDTO dto
    )throws IOException {
        bookService.updateBook(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> delete(
            @Valid @PathVariable("id") Long id
    )throws IOException {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll(){
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/page")
    public ResponseEntity<List<BookDTO>> getPageAllList(
            @Valid @RequestParam("pageNumber") int pageNumber,
            @Valid @RequestParam("pageSize") int pageSize
    ){
        List<BookDTO> books = bookService.getPageAllBooks(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/by-genres")
    public ResponseEntity<List<GetBookWithGenresDTO>> getBooksWithGenres(){
        List<GetBookWithGenresDTO> books = bookService.getBooksWithGenre();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/by-publishers")
    public ResponseEntity<List<GetBookWithPublishersDTO>> getBooksWithPublishers(){
        List<GetBookWithPublishersDTO> books = bookService.getBooksWithPublisher();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/by-authors")
    public ResponseEntity<List<GetBookWithAuthorsDTO>> getBooksWithAuthors(){
        List<GetBookWithAuthorsDTO> books = bookService.getBooksWithAuthor();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@Valid @PathVariable("id") Long id){
        BookDTO book = bookService.getByIdBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }
}