package com.tarkhan.backend.service;

import com.tarkhan.backend.model.readBook.CreateReadBookDTO;
import com.tarkhan.backend.model.readBook.ReadBookDTO;
import com.tarkhan.backend.model.readBook.UpdateReadBookDTO;

import java.util.List;

public interface ReadBookService {
    void createReadBook(CreateReadBookDTO request);
    void updateReadBook(Long id, UpdateReadBookDTO request);
    void deleteReadBook(Long id);
    ReadBookDTO getReadBook(Long id);
    List<ReadBookDTO> getReadBooks();
    List<ReadBookDTO> getReadBooksByPage(int pageNumber, int pageSize);
}
