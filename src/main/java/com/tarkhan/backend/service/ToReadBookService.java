package com.tarkhan.backend.service;

import com.tarkhan.backend.model.readBook.CreateReadBookDTO;
import com.tarkhan.backend.model.readBook.ReadBookDTO;
import com.tarkhan.backend.model.readBook.UpdateReadBookDTO;

import java.util.List;

public interface ToReadBookService {
    void createToReadBook(String token, CreateReadBookDTO request);
    void updateToReadBook(String token, Long id, UpdateReadBookDTO request);
    void deleteToReadBook(String token, Long id);
    ReadBookDTO getToReadBook(String token, Long id);
    List<ReadBookDTO> getToReadBooks(String token);
    List<ReadBookDTO> getToReadBooksByPage(String token, int pageNumber, int pageSize);
}
