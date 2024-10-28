package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.book.*;
import com.tarkhan.backend.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Validated
@Tag(name = "Book Management", description = "APIs for managing books in the system")
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/admin", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Create a new book",
            description = "Creates a new book entry in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
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

    @PutMapping(value = "/admin/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Update an existing book",
            description = "Updates the details of an existing book by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
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

    @DeleteMapping("/admin/{id}")
    @Operation(summary = "Delete a book", description = "Deletes a book entry by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
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
    @Operation(summary = "Get all books",
            description = "Retrieves a list of all books in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of books retrieved successfully")
    })
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID",
            description = "Retrieves a specific book from the system by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookDTO> getBookById(
            @Parameter(description = "ID of the book to retrieve") @Valid @PathVariable Long id
    ) {
        BookDTO book = bookService.getByIdBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    @Operation(summary = "Get books by page",
            description = "Retrieves a paginated list of books.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of books retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    public ResponseEntity<List<BookDTO>> getBooksByPage(
            @Parameter(description = "Page number to retrieve") @Valid @RequestParam("pageNumber") int pageNumber,
            @Parameter(description = "Number of books per page") @Valid @RequestParam("pageSize") int pageSize
    ) {
        List<BookDTO> books = bookService.getPageAllBooks(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/genre/{genreName}/{pageNumber}/{pageSize}")
    @Operation(summary = "Get books by genre",
            description = "Retrieves a paginated list of books filtered by genre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of books by genre retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    public ResponseEntity<List<GetBooksByGenreDTO>> getBooksByGenre(
            @Parameter(description = "Name of the genre to filter by") @Valid @PathVariable String genreName,
            @Parameter(description = "Page number to retrieve") @Valid @RequestParam("pageNumber") int pageNumber,
            @Parameter(description = "Number of books per page") @Valid @RequestParam("pageSize") int pageSize
    ) {
        List<GetBooksByGenreDTO> books = bookService.getBooksByGenre(genreName, pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/author/{authorName}/{pageNumber}/{pageSize}")
    @Operation(summary = "Get books by author",
            description = "Retrieves a paginated list of books filtered by author.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of books by author retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    public ResponseEntity<List<GetBooksByAuthorDTO>> getBooksByAuthor(
            @Parameter(description = "Name of the author to filter by") @Valid @PathVariable String authorName,
            @Parameter(description = "Page number to retrieve") @Valid @RequestParam("pageNumber") int pageNumber,
            @Parameter(description = "Number of books per page") @Valid @RequestParam("pageSize") int pageSize
    ) {
        List<GetBooksByAuthorDTO> books = bookService.getBooksByAuthor(authorName, pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/publisher/{publisherName}/{pageNumber}/{pageSize}")
    @Operation(summary = "Get books by publisher",
            description = "Retrieves a paginated list of books filtered by publisher.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of books by publisher retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Publisher not found"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    public ResponseEntity<List<GetBooksByPublisherDTO>> getBooksByPublisher(
            @Parameter(description = "Name of the publisher to filter by") @Valid @PathVariable String publisherName,
            @Parameter(description = "Page number to retrieve") @Valid @RequestParam("pageNumber") int pageNumber,
            @Parameter(description = "Number of books per page") @Valid @RequestParam("pageSize") int pageSize
    ) {
        List<GetBooksByPublisherDTO> books = bookService.getBooksByPublisher(publisherName, pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/genres")
    @Operation(summary = "Get all genres",
            description = "Retrieves a list of books with their genres.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of books with genres retrieved successfully")
    })
    public ResponseEntity<List<GetBookWithGenresDTO>> getBooksByGenre() {
        List<GetBookWithGenresDTO> books = bookService.getBooksWithGenre();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/publishers")
    @Operation(summary = "Get all publishers",
            description = "Retrieves a list of books with their publishers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of books with publishers retrieved successfully")
    })
    public ResponseEntity<List<GetBookWithPublishersDTO>> getBooksByPublisher() {
        List<GetBookWithPublishersDTO> books = bookService.getBooksWithPublisher();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/detailed")
    @Operation(summary = "Get books with details",
            description = "Retrieves a list of books with their detailed information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of books with details retrieved successfully")
    })
    public ResponseEntity<List<GetBookWithDetailsDTO>> getBooksWithDetail() {
        List<GetBookWithDetailsDTO> books = bookService.getBookWithDetails();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/search")
    @Operation(summary = "Search for books",
            description = "Searches for books based on a keyword and filter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books found successfully"),
            @ApiResponse(responseCode = "404", description = "No books found for the given keyword"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<List<BookDTO>> searchBooks(
            @Parameter(description = "Keyword to search for") @Valid @RequestParam String keyword,
            @Parameter(description = "Field to filter by (author, genre, publisher, title)") @Valid @RequestParam String filterBy,
            @Parameter(description = "Page number to retrieve") @Valid @RequestParam("pageNumber") int pageNumber,
            @Parameter(description = "Number of books per page") @Valid @RequestParam("pageSize") int pageSize
    ) {
        List<BookDTO> books = bookService.searchBooks(keyword, filterBy, pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

}
