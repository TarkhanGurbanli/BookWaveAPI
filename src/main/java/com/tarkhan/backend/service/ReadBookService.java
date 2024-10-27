package com.tarkhan.backend.service;

import com.tarkhan.backend.model.readBook.CreateReadBookDTO;
import com.tarkhan.backend.model.readBook.ReadBookDTO;
import com.tarkhan.backend.model.readBook.UpdateReadBookDTO;

import java.util.List;

public interface ReadBookService {
    void createReadBook(String token, CreateReadBookDTO request);
    void updateReadBook(String token, Long id, UpdateReadBookDTO request);
    void deleteReadBook(String token, Long id);
    ReadBookDTO getReadBook(String token, Long id);
    List<ReadBookDTO> getReadBooks(String token);
    List<ReadBookDTO> getReadBooksByPage(String token, int pageNumber, int pageSize);
}
