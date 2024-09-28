package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Author;
import com.tarkhan.backend.entity.Image;
import com.tarkhan.backend.entity.enums.ImageType;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.author.CreateAuthorDTO;
import com.tarkhan.backend.model.author.UpdateAuthorDTO;
import com.tarkhan.backend.repository.AuthorRepository;
import com.tarkhan.backend.service.AuthorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ImageServiceImpl imageService;
    private final ModelMapper modelMapper;

    @Override
    public void createAuthor(CreateAuthorDTO createAuthorDTO) throws IOException {
        Author author = modelMapper.map(createAuthorDTO, Author.class);

        if (createAuthorDTO.getImage() != null && !createAuthorDTO.getImage().isEmpty()) {
            File tempFile = File.createTempFile("author-", createAuthorDTO.getImage().getOriginalFilename());
            createAuthorDTO.getImage().transferTo(tempFile);

            Image profileImage = imageService.uploadImageToDrive(tempFile, ImageType.AUTHOR);

            author.setProfileImage(profileImage);

            tempFile.delete();
        }

        authorRepository.save(author);

    }

    @Override
    public void updateAuthor(Long id, UpdateAuthorDTO updateAuthorDTO)
            throws IOException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "ID", id));

        modelMapper.map(updateAuthorDTO, author);

        if (updateAuthorDTO.getImage() != null && !updateAuthorDTO.getImage().isEmpty()) {
            Image oldImage = author.getProfileImage();
            imageService.deleteImageFromDrive(oldImage);

            File tempFile = File.createTempFile("author-", updateAuthorDTO.getImage().getOriginalFilename());
            updateAuthorDTO.getImage().transferTo(tempFile);

            Image newImage = imageService.uploadImageToDrive(tempFile, ImageType.AUTHOR);
            author.setProfileImage(newImage);
            tempFile.delete();
        }

        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) throws IOException {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));

        if (author.getProfileImage() != null) {
            imageService.deleteImageFromDrive(author.getProfileImage());
        }

        authorRepository.deleteById(id);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(author -> {
                    AuthorDTO authorDTO = modelMapper.map(author, AuthorDTO.class);
                        authorDTO.setImageId(author.getProfileImage().getId());

                    return authorDTO;
                })
                .collect(Collectors.toList());
    }
}