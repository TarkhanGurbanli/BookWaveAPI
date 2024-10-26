package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.readBook.CreateReadBookDTO;
import com.tarkhan.backend.model.readBook.ReadBookDTO;
import com.tarkhan.backend.model.readBook.UpdateReadBookDTO;
import com.tarkhan.backend.service.ReadBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/readBooks")
@RequiredArgsConstructor
@Validated
public class ReadBookController {

    private final ReadBookService readBookService;

    @GetMapping
    public ResponseEntity<List<ReadBookDTO>> readBooks() {
        List<ReadBookDTO> readBookDTOS = readBookService.getReadBooks();
        return ResponseEntity.ok(readBookDTOS);
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<List<ReadBookDTO>> readBooksByPage(
            @RequestParam int pageNumber,
            @RequestParam int pageSize)
    {
        List<ReadBookDTO> readBookDTOS = readBookService.getReadBooksByPage(pageNumber, pageSize);
        return ResponseEntity.ok(readBookDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadBookDTO> readBooksById(@PathVariable Long id)
    {
        ReadBookDTO readBookDTO = readBookService.getReadBook(id);
        return ResponseEntity.ok(readBookDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseModel> createReadBook(
            @RequestBody @Valid CreateReadBookDTO request
    ){
     readBookService.createReadBook(request);
     return ResponseEntity.status(HttpStatus.CREATED).body(
             new ResponseModel(
                     Constants.STATUS_CREATED,
                     Constants.MESSAGE_CREATED_SUCCESSFULLY
             )
     );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel> updateReadBook(
            @PathVariable @Valid Long id,
            @RequestBody @Valid UpdateReadBookDTO request
    ){
        readBookService.updateReadBook(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deleteReadBook(
            @PathVariable @Valid Long id
    ){
        readBookService.deleteReadBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}
