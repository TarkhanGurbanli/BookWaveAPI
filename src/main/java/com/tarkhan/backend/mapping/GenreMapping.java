package com.tarkhan.backend.mapping;

import com.tarkhan.backend.entity.Book;
import com.tarkhan.backend.entity.Genre;
import com.tarkhan.backend.model.book.BookDTO;
import com.tarkhan.backend.model.genre.GetGenreWithBooksDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreMapping {
    public GetGenreWithBooksDTO toGenreWithBooksDTO(Genre genre) {
        GetGenreWithBooksDTO dto = new GetGenreWithBooksDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());

        List<Book> books = genre.getBooks();

        List<BookDTO> bookDTOs = new ArrayList<>();

        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
                BookDTO bookDTO = new BookDTO();
                bookDTO.setId(book.getId());
                bookDTO.setTitle(book.getTitle());
                bookDTO.setDescription(book.getDescription());
                bookDTO.setRating(book.getRating());
                bookDTO.setAuthorId(genre.getId());
                bookDTO.setPublisherId(book.getPublisher().getId());
                bookDTO.setGenreId(book.getGenre().getId());

                bookDTO.setImageId(book.getCoverImage().getId());
                bookDTOs.add(bookDTO);
            }
        }
        dto.setBooks(bookDTOs);
        return dto;
    }
}