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
    BookDTO getByIdBook(Long id);
}
