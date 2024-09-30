package com.tarkhan.backend.mapping;

import com.tarkhan.backend.entity.Author;
import com.tarkhan.backend.entity.Book;
import com.tarkhan.backend.entity.Quote;
import com.tarkhan.backend.model.quote.CreateQuoteDTO;
import com.tarkhan.backend.model.quote.GetQuoteWithDetailDTO;
import com.tarkhan.backend.model.quote.UpdateQuoteDTO;
import org.springframework.stereotype.Component;

@Component
public class QuoteMapping {

    public Quote toCreateDto(CreateQuoteDTO dto){
        Quote quote = new Quote();
        quote.setText(dto.getText());

        Author author = new Author();
        author.setId(dto.getAuthorId());
        quote.setAuthor(author);

        Book book = new Book();
        book.setId(dto.getBookId());
        quote.setBook(book);

        return quote;
    }

    public GetQuoteWithDetailDTO toGetQuoteWithDetail(Quote quote){
        GetQuoteWithDetailDTO dto = new GetQuoteWithDetailDTO();
        dto.setId(quote.getId());
        dto.setText(quote.getText());

        Author author = quote.getAuthor();
        dto.setAuthorName(author.getName());

        Book book = quote.getBook();
        dto.setBookName(book.getTitle());

        return dto;
    }
}
