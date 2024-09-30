package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.quote.CreateQuoteDTO;
import com.tarkhan.backend.model.quote.GetQuoteWithDetailDTO;
import com.tarkhan.backend.model.quote.QuoteDTO;
import com.tarkhan.backend.model.quote.UpdateQuoteDTO;
import com.tarkhan.backend.service.QuoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quotes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Quote Management")
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping("/admin")
    public ResponseEntity<ResponseModel> createQuote(
            @Valid @RequestBody CreateQuoteDTO dto
    ){
        quoteService.createQuote(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<ResponseModel> updateQuote(
            @Valid @PathVariable Long id,
            @Valid @RequestBody UpdateQuoteDTO dto
    ){
        quoteService.updateQuote(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ResponseModel> deleteQuote(
            @Valid @PathVariable Long id
    ){
        quoteService.deleteQuote(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteDTO> getQuoteById(
            @Valid @PathVariable Long id
    ){
        QuoteDTO dto =  quoteService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<GetQuoteWithDetailDTO> getQuoteDetailsById(
            @Valid @PathVariable Long id
    ){
        GetQuoteWithDetailDTO dto =  quoteService.getQuoteByIdDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<QuoteDTO>> getAllQuotes(){
        List<QuoteDTO> dtos =  quoteService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/details")
    public ResponseEntity<List<GetQuoteWithDetailDTO>> getAllQuotesWithDetails(){
        List<GetQuoteWithDetailDTO> dtos =  quoteService.getQuotesWithDetails();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/page")
    public ResponseEntity<List<QuoteDTO>> getQuotesByPage(
            @Valid @RequestParam int page,
            @Valid @RequestParam int size
    ){
        List<QuoteDTO> dtos =  quoteService.getQuotesByPage(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/details/page")
    public ResponseEntity<List<GetQuoteWithDetailDTO>> getQuotesWithDetailsByPage(
            @Valid @RequestParam int page,
            @Valid @RequestParam int size
    ){
        List<GetQuoteWithDetailDTO> dtos =  quoteService.getQuotesWithDetailsByPage(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
}