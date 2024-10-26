package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.ReadBook;
import com.tarkhan.backend.entity.ToReadBook;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.readBook.CreateReadBookDTO;
import com.tarkhan.backend.model.readBook.ReadBookDTO;
import com.tarkhan.backend.model.readBook.UpdateReadBookDTO;
import com.tarkhan.backend.repository.ToReadBookRepository;
import com.tarkhan.backend.service.ToReadBookService;
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
    private final ModelMapper modelMapper;

    @Override
    public void createToReadBook(CreateReadBookDTO request) {
        toReadBookRepository.save(modelMapper.map(request, ToReadBook.class));
    }

    @Override
    public void updateToReadBook(Long id, UpdateReadBookDTO request) {
        ToReadBook toReadBook = toReadBookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ToReadBook", "ID", id));

        modelMapper.map(toReadBook, request);
        toReadBookRepository.save(toReadBook);
    }

    @Override
    public void deleteToReadBook(Long id) {
        ToReadBook toReadBook = toReadBookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ToReadBook", "ID", id));

        toReadBookRepository.delete(toReadBook);
    }

    @Override
    public ReadBookDTO getToReadBook(Long id) {
        ToReadBook toReadBook = toReadBookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ToReadBook", "ID", id));

        return modelMapper.map(toReadBook, ReadBookDTO.class);
    }

    @Override
    public List<ReadBookDTO> getToReadBooks() {
        List<ToReadBook> toReadBooks = toReadBookRepository.findAll();
        return toReadBooks.stream().map(
                        toReadBook -> modelMapper.map(toReadBook, ReadBookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReadBookDTO> getToReadBooksByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<ToReadBook> toReadBooks = toReadBookRepository.findAll(pageable).getContent();
        return toReadBooks.stream().map(
                        toReadBook -> modelMapper.map(toReadBook, ReadBookDTO.class))
                .collect(Collectors.toList());
    }
}
