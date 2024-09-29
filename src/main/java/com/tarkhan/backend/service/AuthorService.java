package com.tarkhan.backend.service;

import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.author.CreateAuthorDTO;
import com.tarkhan.backend.model.author.GetAuthorWithBooksDTO;
import com.tarkhan.backend.model.author.UpdateAuthorDTO;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void createAuthor(CreateAuthorDTO createAuthorDTO) throws IOException;
    void updateAuthor(Long id, UpdateAuthorDTO updateAuthorDTO) throws IOException;
    void deleteAuthor(Long id) throws IOException;
    List<AuthorDTO> getAllAuthors();
    List<GetAuthorWithBooksDTO> getAllAuthorsWithBooks();
    List<AuthorDTO> getPageAllAuthors(int pageNumber, int pageSize);
    AuthorDTO getByIdAuthor(Long id);
}
