package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Quote;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.mapping.QuoteMapping;
import com.tarkhan.backend.model.quote.CreateQuoteDTO;
import com.tarkhan.backend.model.quote.GetQuoteWithDetailDTO;
import com.tarkhan.backend.model.quote.QuoteDTO;
import com.tarkhan.backend.model.quote.UpdateQuoteDTO;
import com.tarkhan.backend.repository.QuoteRepository;
import com.tarkhan.backend.service.QuoteService;
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
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapping quoteMapping;
    private final ModelMapper modelMapper;

    @Override
    public void createQuote(CreateQuoteDTO dto) {
        Quote quote = quoteMapping.toCreateDto(dto);
        quoteRepository.save(quote);
    }

    @Override
    public void updateQuote(Long id, UpdateQuoteDTO dto) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quote", "ID", id));
        modelMapper.map(quote, dto);
        quoteRepository.save(quote);
    }

    @Override
    public void deleteQuote(Long id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quote", "ID", id));
        quoteRepository.delete(quote);
    }

    @Override
    public QuoteDTO getById(Long id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quote", "ID", id));
        return modelMapper.map(quote, QuoteDTO.class);
    }

    @Override
    public List<QuoteDTO> getAll() {
        List<Quote> quotes = quoteRepository.findAll();
        return quotes.stream()
                .map(quote -> modelMapper.map(quote, QuoteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuoteDTO> getQuotesByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Quote> quotes = quoteRepository.findAll(pageable).getContent();
        return quotes.stream()
                .map(quote -> modelMapper.map(quote, QuoteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetQuoteWithDetailDTO> getQuotesWithDetails() {
        List<Quote> quotes = quoteRepository.findAll();
        return quotes.stream()
                .map(quoteMapping::toGetQuoteWithDetail)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetQuoteWithDetailDTO> getQuotesWithDetailsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Quote> quotes = quoteRepository.findAll(pageable).getContent();
        return quotes.stream()
                .map(quoteMapping::toGetQuoteWithDetail)
                .collect(Collectors.toList());
    }

    @Override
    public GetQuoteWithDetailDTO getQuoteByIdDetail(Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new ResourceNotFoundException("Quote", "ID", quoteId));

        return quoteMapping.toGetQuoteWithDetail(quote);
    }
}
