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
    public ResponseEntity<ResponseModel> createBook(
            @Valid @ModelAttribute CreateBookDTO dto
    ) throws IOException {
        bookService.createBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseModel> updateBook(
            @Valid @PathVariable("id") Long id,
            @Valid @ModelAttribute UpdateBookDTO dto
    ) throws IOException {
        bookService.updateBook(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deleteBook(
            @Valid @PathVariable("id") Long id
    ) throws IOException {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/page")
    public ResponseEntity<List<BookDTO>> getBooksByPage(
            @Valid @RequestParam("pageNumber") int pageNumber,
            @Valid @RequestParam("pageSize") int pageSize
    ) {
        List<BookDTO> books = bookService.getPageAllBooks(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<GetBookWithGenresDTO>> getBooksByGenre() {
        List<GetBookWithGenresDTO> books = bookService.getBooksWithGenre();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/publishers")
    public ResponseEntity<List<GetBookWithPublishersDTO>> getBooksByPublisher() {
        List<GetBookWithPublishersDTO> books = bookService.getBooksWithPublisher();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/detailed")
    public ResponseEntity<List<GetBookWithDetailsDTO>> getBooksWithDetails() {
        List<GetBookWithDetailsDTO> books = bookService.getBookWithDetails();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<GetBookWithAuthorsDTO>> getBooksByAuthor() {
        List<GetBookWithAuthorsDTO> books = bookService.getBooksWithAuthor();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@Valid @PathVariable("id") Long id) {
        BookDTO book = bookService.getByIdBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/{bookId}/detailed")
    public ResponseEntity<GetBookWithDetailsDTO> getBookDetailsById(
            @Valid @PathVariable("bookId") Long bookId
    ) {
        GetBookWithDetailsDTO book = bookService.getBookByDetails(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/{bookId}/genres")
    public ResponseEntity<GetBookWithGenresDTO> getBookGenresById(
            @Valid @PathVariable("bookId") Long bookId
    ) {
        GetBookWithGenresDTO book = bookService.getBookByGenre(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/{bookId}/authors")
    public ResponseEntity<GetBookWithAuthorsDTO> getBookAuthorsById(
            @Valid @PathVariable("bookId") Long bookId
    ) {
        GetBookWithAuthorsDTO book = bookService.getBookByAuthor(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/{bookId}/publishers")
    public ResponseEntity<GetBookWithPublishersDTO> getBookPublisherById(
            @Valid @PathVariable("bookId") Long bookId
    ) {
        GetBookWithPublishersDTO book = bookService.getBookByPublisher(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }
}