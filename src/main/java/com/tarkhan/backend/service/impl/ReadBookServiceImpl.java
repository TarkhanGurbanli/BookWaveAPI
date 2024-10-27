package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Book;
import com.tarkhan.backend.entity.ReadBook;
import com.tarkhan.backend.entity.User;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.auth.JWTModel;
import com.tarkhan.backend.model.readBook.CreateReadBookDTO;
import com.tarkhan.backend.model.readBook.ReadBookDTO;
import com.tarkhan.backend.model.readBook.UpdateReadBookDTO;
import com.tarkhan.backend.repository.BookRepository;
import com.tarkhan.backend.repository.ReadBookRepository;
import com.tarkhan.backend.repository.UserRepository;
import com.tarkhan.backend.service.ReadBookService;
import com.tarkhan.backend.service.auth.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReadBookServiceImpl implements ReadBookService {

    private final ReadBookRepository readBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @Override
    public void createReadBook(String token, CreateReadBookDTO request) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            Book book = bookRepository.findById(request.getBookId()).orElseThrow(
                    () -> new ResourceNotFoundException("Book", "ID", request.getBookId())
            );

            ReadBook readBook = new ReadBook();
            readBook.setUser(user);
            readBook.setBook(book);

            readBookRepository.save(readBook);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ReadBook creation failed" + e.getMessage());
        }
    }

    @Override
    public void updateReadBook(String token, Long id, UpdateReadBookDTO request) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            ReadBook readBook = readBookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("ReadBook", "ID", id)
            );

            if (!readBook.getUser().getId().equals(userId)) {
                throw new ResourceNotFoundException("ReadBook", "User ID", userId);
            }

            Book book = bookRepository.findById(request.getBookId()).orElseThrow(
                    () -> new ResourceNotFoundException("Book", "ID", request.getBookId())
            );

            readBook.setBook(book);
            readBookRepository.save(readBook);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("ReadBook update failed", e);
        }
    }

    @Override
    public void deleteReadBook(String token, Long id) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            ReadBook readBook = readBookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("ReadBook", "ID", id)
            );

            if (!readBook.getUser().getId().equals(userId)) {
                throw new ResourceNotFoundException("ReadBook", "User ID", userId);
            }

            readBookRepository.delete(readBook);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("ReadBook deletion failed", e);
        }
    }

    @Override
    public ReadBookDTO getReadBook(String token, Long id) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            ReadBook readBook = readBookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("ReadBook", "ID", id)
            );

            if (!readBook.getUser().getId().equals(userId)) {
                throw new ResourceNotFoundException("ReadBook", "User ID", userId);
            }

            return modelMapper.map(readBook, ReadBookDTO.class);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get ReadBook", e);
        }
    }


    @Override
    public List<ReadBookDTO> getReadBooks(String token) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            List<ReadBook> readBooks = readBookRepository.findAllByUser(user);
            return readBooks.stream()
                    .map((readBook -> modelMapper.map(readBook, ReadBookDTO.class)))
                    .collect(Collectors.toList());

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get ReadBooks", e);
        }
    }

    @Override
    public List<ReadBookDTO> getReadBooksByPage(String token, int pageNumber, int pageSize) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
            List<ReadBook> readBooks =  readBookRepository.findAllByUser(user, pageable);
            return readBooks.stream()
                    .map((readBook -> modelMapper.map(readBook, ReadBookDTO.class)))
                    .collect(Collectors.toList());

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get ReadBooks by page", e);
        }
    }
}
