package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.ReadBook;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.readBook.CreateReadBookDTO;
import com.tarkhan.backend.model.readBook.ReadBookDTO;
import com.tarkhan.backend.model.readBook.UpdateReadBookDTO;
import com.tarkhan.backend.repository.ReadBookRepository;
import com.tarkhan.backend.service.ReadBookService;
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
    private final ModelMapper modelMapper;

    @Override
    public void createReadBook(CreateReadBookDTO request) {
        readBookRepository.save(modelMapper.map(request, ReadBook.class));
    }

    @Override
    public void updateReadBook(Long id, UpdateReadBookDTO request) {
        ReadBook readBook = readBookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ReadBook", "ID", id));

        modelMapper.map(readBook, request);
        readBookRepository.save(readBook);
    }

    @Override
    public void deleteReadBook(Long id) {
        ReadBook readBook = readBookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ReadBook", "ID", id));

        readBookRepository.delete(readBook);
    }

    @Override
    public ReadBookDTO getReadBook(Long id) {
        ReadBook readBook = readBookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ReadBook", "ID", id));

        return modelMapper.map(readBook, ReadBookDTO.class);
    }

    @Override
    public List<ReadBookDTO> getReadBooks() {
        List<ReadBook> readBooks = readBookRepository.findAll();
        return readBooks.stream().map(
                readBook -> modelMapper.map(readBook, ReadBookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReadBookDTO> getReadBooksByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<ReadBook> readBooks = readBookRepository.findAll(pageable).getContent();
        return readBooks.stream().map(
                readBook -> modelMapper.map(readBook, ReadBookDTO.class))
                .collect(Collectors.toList());
    }
}
