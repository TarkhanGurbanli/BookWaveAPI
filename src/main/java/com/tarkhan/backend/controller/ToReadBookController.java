package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.readBook.CreateReadBookDTO;
import com.tarkhan.backend.model.readBook.ReadBookDTO;
import com.tarkhan.backend.model.readBook.UpdateReadBookDTO;
import com.tarkhan.backend.service.ToReadBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/toReadBooks")
@RequiredArgsConstructor
@Validated
public class ToReadBookController {

    private final ToReadBookService toReadBookService;

    @GetMapping
    public ResponseEntity<List<ReadBookDTO>> toReadBooks() {
        List<ReadBookDTO> readBookDTOS = toReadBookService.getToReadBooks();
        return ResponseEntity.ok(readBookDTOS);
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<List<ReadBookDTO>> toReadBooksByPage(
            @RequestParam int pageNumber,
            @RequestParam int pageSize)
    {
        List<ReadBookDTO> readBookDTOS = toReadBookService.getToReadBooksByPage(pageNumber, pageSize);
        return ResponseEntity.ok(readBookDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadBookDTO> toReadBooksById(@PathVariable Long id)
    {
        ReadBookDTO readBookDTO = toReadBookService.getToReadBook(id);
        return ResponseEntity.ok(readBookDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseModel> createToReadBook(
            @RequestBody @Valid CreateReadBookDTO request
    ){
        toReadBookService.createToReadBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel> updateToReadBook(
            @PathVariable @Valid Long id,
            @RequestBody @Valid UpdateReadBookDTO request
    ){
        toReadBookService.updateToReadBook(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deleteToReadBook(
            @PathVariable @Valid Long id
    ){
        toReadBookService.deleteToReadBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}
