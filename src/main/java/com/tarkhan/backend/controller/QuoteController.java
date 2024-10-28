package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.quote.CreateQuoteDTO;
import com.tarkhan.backend.model.quote.GetQuoteWithDetailDTO;
import com.tarkhan.backend.model.quote.QuoteDTO;
import com.tarkhan.backend.model.quote.UpdateQuoteDTO;
import com.tarkhan.backend.service.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "Create a new quote", description = "Creates a new quote in the system.")
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
    @Operation(summary = "Update an existing quote",
            description = "Updates an existing quote by ID.")
    public ResponseEntity<ResponseModel> updateQuote(
            @Parameter(description = "ID of the quote to update") @Valid @PathVariable Long id,
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
    @Operation(summary = "Delete a quote", description = "Deletes a quote by ID.")
    public ResponseEntity<ResponseModel> deleteQuote(
            @Parameter(description = "ID of the quote to delete") @Valid @PathVariable Long id
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
    @Operation(summary = "Get quote by ID",
            description = "Retrieves a quote by its ID.")
    public ResponseEntity<QuoteDTO> getQuoteById(
            @Parameter(description = "ID of the quote to retrieve") @Valid @PathVariable Long id
    ){
        QuoteDTO dto =  quoteService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{id}/details")
    @Operation(summary = "Get quote details by ID",
            description = "Retrieves detailed information about a quote by its ID.")
    public ResponseEntity<GetQuoteWithDetailDTO> getQuoteDetailsById(
            @Parameter(description = "ID of the quote to retrieve details for") @Valid @PathVariable Long id
    ){
        GetQuoteWithDetailDTO dto =  quoteService.getQuoteByIdDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping
    @Operation(summary = "Get all quotes", description = "Retrieves all quotes.")
    public ResponseEntity<List<QuoteDTO>> getAllQuotes(){
        List<QuoteDTO> dtos =  quoteService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/details")
    @Operation(summary = "Get all quotes with details",
            description = "Retrieves all quotes along with their details.")
    public ResponseEntity<List<GetQuoteWithDetailDTO>> getAllQuotesWithDetails(){
        List<GetQuoteWithDetailDTO> dtos =  quoteService.getQuotesWithDetails();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/page")
    @Operation(summary = "Get quotes by page",
            description = "Retrieves quotes by page number and size.")
    public ResponseEntity<List<QuoteDTO>> getQuotesByPage(
            @Parameter(description = "Page number to retrieve") @Valid @RequestParam int page,
            @Parameter(description = "Size of the page") @Valid @RequestParam int size
    ){
        List<QuoteDTO> dtos =  quoteService.getQuotesByPage(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/details/page")
    @Operation(summary = "Get quotes with details by page",
            description = "Retrieves quotes with details by page number and size.")
    public ResponseEntity<List<GetQuoteWithDetailDTO>> getQuotesWithDetailsByPage(
            @Parameter(description = "Page number to retrieve") @Valid @RequestParam int page,
            @Parameter(description = "Size of the page") @Valid @RequestParam int size
    ){
        List<GetQuoteWithDetailDTO> dtos =  quoteService.getQuotesWithDetailsByPage(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
}
