package com.tarkhan.backend.service;

import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.author.CreateAuthorDTO;
import com.tarkhan.backend.model.author.UpdateAuthorDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void createAuthor(CreateAuthorDTO createAuthorDTO, MultipartFile image) throws IOException;
    void updateAuthor(Long id, UpdateAuthorDTO updateAuthorDTO, MultipartFile image) throws IOException;
    void deleteAuthor(Long id) throws IOException;
    List<AuthorDTO> getAllAuthors();
}
