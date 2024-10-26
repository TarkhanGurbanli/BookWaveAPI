package com.tarkhan.backend.service;

import com.tarkhan.backend.model.readBook.CreateReadBookDTO;
import com.tarkhan.backend.model.readBook.ReadBookDTO;
import com.tarkhan.backend.model.readBook.UpdateReadBookDTO;

import java.util.List;

public interface ToReadBookService {
    void createToReadBook(CreateReadBookDTO request);
    void updateToReadBook(Long id, UpdateReadBookDTO request);
    void deleteToReadBook(Long id);
    ReadBookDTO getToReadBook(Long id);
    List<ReadBookDTO> getToReadBooks();
    List<ReadBookDTO> getToReadBooksByPage(int pageNumber, int pageSize);
}
