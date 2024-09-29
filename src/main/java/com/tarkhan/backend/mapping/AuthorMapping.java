package com.tarkhan.backend.mapping;

import com.tarkhan.backend.entity.Author;
import com.tarkhan.backend.entity.Book;
import com.tarkhan.backend.model.author.GetAuthorWithBooksDTO;
import com.tarkhan.backend.model.book.BookDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMapping {

    public GetAuthorWithBooksDTO getAuthorWithBooksDTO(Author author) {
        GetAuthorWithBooksDTO getAuthorWithBooksDTO = new GetAuthorWithBooksDTO();

        getAuthorWithBooksDTO.setId(author.getId());
        getAuthorWithBooksDTO.setName(author.getName());
        getAuthorWithBooksDTO.setBirthDate(author.getBirthDate());
        getAuthorWithBooksDTO.setNationality(author.getNationality());
        getAuthorWithBooksDTO.setBiography(author.getBiography());

            getAuthorWithBooksDTO.setImageId(author.getProfileImage().getId());

        List<Book> books = author.getBooks();

        List<BookDTO> bookDTOs = new ArrayList<>();

        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
                BookDTO bookDTO = new BookDTO();
                bookDTO.setId(book.getId());
                bookDTO.setTitle(book.getTitle());
                bookDTO.setDescription(book.getDescription());
                bookDTO.setRating(book.getRating());
                bookDTO.setAuthorId(author.getId());
                bookDTO.setPublisherId(book.getPublisher().getId());
                bookDTO.setGenreId(book.getGenre().getId());

                    bookDTO.setImageId(book.getCoverImage().getId());
                bookDTOs.add(bookDTO);
            }
        }
        getAuthorWithBooksDTO.setBooks(bookDTOs);
        return getAuthorWithBooksDTO;
    }
}
