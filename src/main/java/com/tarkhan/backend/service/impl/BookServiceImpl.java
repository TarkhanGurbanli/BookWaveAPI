package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.*;
import com.tarkhan.backend.entity.enums.ImageType;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.mapping.BookMapping;
import com.tarkhan.backend.model.book.*;
import com.tarkhan.backend.repository.AuthorRepository;
import com.tarkhan.backend.repository.BookRepository;
import com.tarkhan.backend.repository.GenreRepository;
import com.tarkhan.backend.repository.PublisherRepository;
import com.tarkhan.backend.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;
    private final ImageServiceImpl imageService;
    private final BookMapping bookMapping;
    private final ModelMapper modelMapper;

    @Override
    public void createBook(CreateBookDTO createBookDTO) throws IOException {
        Book book = new Book();

        book.setTitle(createBookDTO.getTitle());
        book.setDescription(createBookDTO.getDescription());
        book.setRating(createBookDTO.getRating());

        if (createBookDTO.getImage() != null && !createBookDTO.getImage().isEmpty()) {
            File tempFile = File.createTempFile("book-", createBookDTO.getImage().getOriginalFilename());
            createBookDTO.getImage().transferTo(tempFile);

            Image coverImage = imageService.uploadImageToDrive(tempFile, ImageType.BOOK);
            book.setCoverImage(coverImage);
            tempFile.delete();
        }

        Author author = authorRepository.findById(createBookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", "ID", createBookDTO.getAuthorId()));

        Publisher publisher = publisherRepository.findById(createBookDTO.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", "ID", createBookDTO.getPublisherId()));

        Genre genre = genreRepository.findById(createBookDTO.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "ID", createBookDTO.getGenreId()));

        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setGenre(genre);

        bookRepository.save(book);
    }


    @Override
    public void updateBook(Long id, UpdateBookDTO updateBookDTO) throws IOException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", id));

        modelMapper.map(updateBookDTO, book);

        if (updateBookDTO.getImage() != null && !updateBookDTO.getImage().isEmpty()) {
            Image oldImage = book.getCoverImage();
            imageService.deleteImageFromDrive(oldImage);

            File tempFile = File.createTempFile("author-", updateBookDTO.getImage().getOriginalFilename());
            updateBookDTO.getImage().transferTo(tempFile);

            Image newImage = imageService.uploadImageToDrive(tempFile, ImageType.AUTHOR);
            book.setCoverImage(newImage);
            tempFile.delete();
        }

        Author author = authorRepository.findById(updateBookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", "ID", updateBookDTO.getAuthorId()));

        Publisher publisher = publisherRepository.findById(updateBookDTO.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", "ID", updateBookDTO.getPublisherId()));

        Genre genre = genreRepository.findById(updateBookDTO.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "ID", updateBookDTO.getGenreId()));

        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setGenre(genre);

        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) throws IOException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", id));
        bookRepository.delete(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getPageAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Book> books = bookRepository.findAll(pageable).getContent();
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetBookWithGenresDTO> getBooksWithGenre() {
        List<Book> books = bookRepository.findAll();

        List<GetBookWithGenresDTO> dtos = books.stream()
                .map(bookMapping::toBookWithGenres)
                .collect(Collectors.toList());

        return dtos;
    }


    @Override
    public List<GetBookWithPublishersDTO> getBooksWithPublisher() {
        List<Book> books = bookRepository.findAll();
        List<GetBookWithPublishersDTO> dtos = books.stream()
                .map(bookMapping::toBookWithPublishers)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<GetBookWithAuthorsDTO> getBooksWithAuthor() {
        List<Book> books = bookRepository.findAll();
        List<GetBookWithAuthorsDTO> dtos = books.stream()
                .map(bookMapping::toBookWithAuthors)
                .collect(Collectors.toList());
        return dtos;
    }


    @Override
    public BookDTO getByIdBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", id));

        return modelMapper.map(book, BookDTO.class);
    }
}