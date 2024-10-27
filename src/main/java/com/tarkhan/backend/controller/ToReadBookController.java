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
    public ResponseEntity<List<ReadBookDTO>> readBooks(@RequestHeader("Authorization") String token) {
        List<ReadBookDTO> readBookDTOS = toReadBookService.getToReadBooks(token);
        return ResponseEntity.ok(readBookDTOS);
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<List<ReadBookDTO>> readBooksByPage(
            @RequestHeader("Authorization") String token,
            @RequestParam int pageNumber,
            @RequestParam int pageSize)
    {
        List<ReadBookDTO> readBookDTOS = toReadBookService.getToReadBooksByPage(token, pageNumber, pageSize);
        return ResponseEntity.ok(readBookDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadBookDTO> readBooksById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    )
    {
        ReadBookDTO readBookDTO = toReadBookService.getToReadBook(token, id);
        return ResponseEntity.ok(readBookDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseModel> createReadBook(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid CreateReadBookDTO request
    ){
        toReadBookService.createToReadBook(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel> updateReadBook(
            @RequestHeader("Authorization") String token,
            @PathVariable @Valid Long id,
            @RequestBody @Valid UpdateReadBookDTO request
    ){
        toReadBookService.updateToReadBook(token, id, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deleteReadBook(
            @RequestHeader("Authorization") String token,
            @PathVariable @Valid Long id
    ){
        toReadBookService.deleteToReadBook(token, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}
