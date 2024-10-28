package com.tarkhan.backend.service;

import com.tarkhan.backend.model.book.*;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void createBook(CreateBookDTO createBookDTO) throws IOException;
    void updateBook(Long id, UpdateBookDTO updateBookDTO) throws IOException;
    void deleteBook(Long id) throws IOException;
    List<BookDTO> getAllBooks();
    List<BookDTO> getPageAllBooks(int pageNumber, int pageSize);
    List<GetBookWithGenresDTO> getBooksWithGenre();
    List<GetBookWithPublishersDTO> getBooksWithPublisher();
    List<GetBookWithAuthorsDTO> getBooksWithAuthor();
    GetBookWithGenresDTO getBookByGenre(Long bookId);
    GetBookWithPublishersDTO getBookByPublisher(Long bookId);
    GetBookWithAuthorsDTO getBookByAuthor(Long bookId);
    List<GetBookWithDetailsDTO> getBookWithDetails();
    GetBookWithDetailsDTO getBookByDetails(Long bookId);
    BookDTO getByIdBook(Long id);
    List<GetBooksByGenreDTO> getBooksByGenre(String genreName, int pageNumber, int pageSize);
    List<GetBooksByAuthorDTO> getBooksByAuthor(String authorName, int pageNumber, int pageSize);
    List<GetBooksByPublisherDTO> getBooksByPublisher(String publisherName, int pageNumber, int pageSize);
    List<BookDTO> searchBooks(String keyword, String filterBy, int pageNumber, int pageSize);
}
