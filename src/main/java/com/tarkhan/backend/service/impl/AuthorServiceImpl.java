package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Author;
import com.tarkhan.backend.entity.Image;
import com.tarkhan.backend.entity.enums.ImageType;
import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.author.CreateAuthorDTO;
import com.tarkhan.backend.model.author.UpdateAuthorDTO;
import com.tarkhan.backend.repository.AuthorRepository;
import com.tarkhan.backend.service.AuthorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public void createAuthor(CreateAuthorDTO createAuthorDTO, MultipartFile image) throws IOException {
        Author author = new Author();
        author.setName(createAuthorDTO.getName());
        author.setBiography(createAuthorDTO.getBiography());
        author.setNationality(createAuthorDTO.getNationality());
        author.setBirthDate(createAuthorDTO.getBirthDate());
        author.setDeathDate(createAuthorDTO.getDeathDate());
        File tempFile = File.createTempFile("author-", image.getOriginalFilename());
        image.transferTo(tempFile);

        Image profileImage = imageService.uploadImageToDrive(tempFile, ImageType.AUTHOR);
        author.setProfileImage(profileImage);

        authorRepository.save(author);
    }

    @Override
    public void updateAuthor(Long id, UpdateAuthorDTO updateAuthorDTO, MultipartFile image) throws IOException {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));

        author.setName(updateAuthorDTO.getName());
        author.setBiography(updateAuthorDTO.getBiography());
        author.setNationality(updateAuthorDTO.getNationality());
        author.setBirthDate(updateAuthorDTO.getBirthDate());
        author.setDeathDate(updateAuthorDTO.getDeathDate());

        if (image != null && !image.isEmpty()) {
            Image oldImage = author.getProfileImage();
            imageService.deleteImageFromDrive(oldImage);

            File tempFile = File.createTempFile("author-", image.getOriginalFilename());
            image.transferTo(tempFile);

            Image newImage = imageService.uploadImageToDrive(tempFile, ImageType.AUTHOR);
            author.setProfileImage(newImage);
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
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .collect(Collectors.toList());
    }
}