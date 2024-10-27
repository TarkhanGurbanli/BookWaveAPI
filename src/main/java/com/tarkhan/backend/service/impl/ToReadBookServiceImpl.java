package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Book;
import com.tarkhan.backend.entity.ReadBook;
import com.tarkhan.backend.entity.ToReadBook;
import com.tarkhan.backend.entity.User;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.auth.JWTModel;
import com.tarkhan.backend.model.readBook.CreateReadBookDTO;
import com.tarkhan.backend.model.readBook.ReadBookDTO;
import com.tarkhan.backend.model.readBook.UpdateReadBookDTO;
import com.tarkhan.backend.repository.BookRepository;
import com.tarkhan.backend.repository.ToReadBookRepository;
import com.tarkhan.backend.repository.UserRepository;
import com.tarkhan.backend.service.ToReadBookService;
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
public class ToReadBookServiceImpl implements ToReadBookService {

    private final ToReadBookRepository toReadBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @Override
    public void createToReadBook(String token, CreateReadBookDTO request) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            Book book = bookRepository.findById(request.getBookId()).orElseThrow(
                    () -> new ResourceNotFoundException("Book", "ID", request.getBookId())
            );

            ToReadBook toReadBook = new ToReadBook();
            toReadBook.setUser(user);
            toReadBook.setBook(book);

            toReadBookRepository.save(toReadBook);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ToReadBook creation failed" + e.getMessage());
        }    }

    @Override
    public void updateToReadBook(String token, Long id, UpdateReadBookDTO request) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            ToReadBook toReadBook = toReadBookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("ReadBook", "ID", id)
            );

            if (!toReadBook.getUser().getId().equals(userId)) {
                throw new ResourceNotFoundException("ReadBook", "User ID", userId);
            }

            Book book = bookRepository.findById(request.getBookId()).orElseThrow(
                    () -> new ResourceNotFoundException("Book", "ID", request.getBookId())
            );

            toReadBook.setBook(book);
            toReadBookRepository.save(toReadBook);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("ToReadBook update failed", e);
        }
    }

    @Override
    public void deleteToReadBook(String token, Long id) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            ToReadBook toReadBook = toReadBookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("ToReadBook", "ID", id)
            );

            if (!toReadBook.getUser().getId().equals(userId)) {
                throw new ResourceNotFoundException("ToReadBook", "User ID", userId);
            }

            toReadBookRepository.delete(toReadBook);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("ReadBook deletion failed", e);
        }
    }

    @Override
    public ReadBookDTO getToReadBook(String token, Long id) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            ToReadBook toReadBook = toReadBookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("ToReadBook", "ID", id)
            );

            if (!toReadBook.getUser().getId().equals(userId)) {
                throw new ResourceNotFoundException("ToReadBook", "User ID", userId);
            }

            return modelMapper.map(toReadBook, ReadBookDTO.class);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get ToReadBook", e);
        }
    }

    @Override
    public List<ReadBookDTO> getToReadBooks(String token) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            List<ToReadBook> toReadBooks = toReadBookRepository.findAllByUser(user);
            return toReadBooks.stream()
                    .map((toReadBook -> modelMapper.map(toReadBook, ReadBookDTO.class)))
                    .collect(Collectors.toList());

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get ToReadBooks", e);
        }
    }

    @Override
    public List<ReadBookDTO> getToReadBooksByPage(String token, int pageNumber, int pageSize) {
        try {
            JWTModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId)
            );

            Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
            List<ToReadBook> toReadBooks =  toReadBookRepository.findAllByUser(user, pageable);
            return toReadBooks.stream()
                    .map((toReadBook -> modelMapper.map(toReadBook, ReadBookDTO.class)))
                    .collect(Collectors.toList());

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get ToReadBooks by page", e);
        }
    }
}
