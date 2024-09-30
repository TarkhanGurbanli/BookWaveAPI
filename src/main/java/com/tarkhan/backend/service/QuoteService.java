package com.tarkhan.backend.service;

import com.tarkhan.backend.model.quote.CreateQuoteDTO;
import com.tarkhan.backend.model.quote.GetQuoteWithDetailDTO;
import com.tarkhan.backend.model.quote.QuoteDTO;
import com.tarkhan.backend.model.quote.UpdateQuoteDTO;

import java.util.List;

public interface QuoteService {
    void createQuote(CreateQuoteDTO dto);
    void updateQuote(Long id, UpdateQuoteDTO dto);
    void deleteQuote(Long id);
    QuoteDTO getById(Long id);
    List<QuoteDTO> getAll();
    List<QuoteDTO> getQuotesByPage(int pageNumber, int pageSize);
    List<GetQuoteWithDetailDTO> getQuotesWithDetails();
    List<GetQuoteWithDetailDTO> getQuotesWithDetailsByPage(int pageNumber, int pageSize);
    GetQuoteWithDetailDTO getQuoteByIdDetail(Long quoteId);
}
