package com.tarkhan.backend.mapping;

import com.tarkhan.backend.entity.Book;
import com.tarkhan.backend.entity.Genre;
import com.tarkhan.backend.entity.Publisher;
import com.tarkhan.backend.model.book.BookDTO;
import com.tarkhan.backend.model.genre.GetGenreWithBooksDTO;
import com.tarkhan.backend.model.publisher.GetPublisherWithBooksDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PublisherMapping {
    public GetPublisherWithBooksDTO getPublisherWithBooksDTO(Publisher publisher) {
        GetPublisherWithBooksDTO dto = new GetPublisherWithBooksDTO();
        dto.setId(publisher.getId());
        dto.setName(publisher.getName());

        List<Book> books = publisher.getBooks();

        List<BookDTO> bookDTOs = new ArrayList<>();

        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
                BookDTO bookDTO = new BookDTO();
                bookDTO.setId(book.getId());
                bookDTO.setTitle(book.getTitle());
                bookDTO.setDescription(book.getDescription());
                bookDTO.setRating(book.getRating());
                bookDTO.setAuthorId(publisher.getId());
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
