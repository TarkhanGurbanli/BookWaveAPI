package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Genre;
import com.tarkhan.backend.exception.BookWaveApiException;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.genre.CreateGenreDTO;
import com.tarkhan.backend.model.genre.GenreDTO;
import com.tarkhan.backend.model.genre.UpdateGenreDTO;
import com.tarkhan.backend.repository.GenreRepository;
import com.tarkhan.backend.service.GenreService;
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
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createGenre(CreateGenreDTO createGenreDTO) {
        Genre genre = modelMapper.map(createGenreDTO, Genre.class);

        if(genreRepository.existsByName(genre.getName())) {
            throw new BookWaveApiException("Genre already exists");
        }

        genreRepository.save(genre);
    }

    @Override
    public void updateGenre(Long id, UpdateGenreDTO updateGenreDTO) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "ID", id));

        boolean existName = genreRepository.existsByName(updateGenreDTO.getName());
        if (existName) {
            throw new BookWaveApiException("Genre already exists");
        }

        modelMapper.map(updateGenreDTO, genre);
        genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "ID", id));

        genreRepository.delete(genre);
    }

    @Override
    public GenreDTO getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "ID", id));

        return modelMapper.map(genre, GenreDTO.class);
    }

    @Override
    public GenreDTO getGenreByName(String name) {
        Genre genre = genreRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "NAME", name));
        return modelMapper.map(genre, GenreDTO.class);
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(genre -> modelMapper
                        .map(genre, GenreDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GenreDTO> getPageAllGenres(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Genre> genres = genreRepository.findAll(pageable).getContent();
        return genres.stream()
                .map(genre -> modelMapper.map(genre, GenreDTO.class))
                .collect(Collectors.toList());
    }
}
