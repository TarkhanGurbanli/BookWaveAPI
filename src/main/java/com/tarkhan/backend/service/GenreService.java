package com.tarkhan.backend.service;

import com.tarkhan.backend.model.genre.CreateGenreDTO;
import com.tarkhan.backend.model.genre.GenreDTO;
import com.tarkhan.backend.model.genre.GetGenreWithBooksDTO;
import com.tarkhan.backend.model.genre.UpdateGenreDTO;

import java.util.List;

public interface GenreService {
    void createGenre(CreateGenreDTO createGenreDTO);
    void updateGenre(Long id, UpdateGenreDTO updateGenreDTO);
    void deleteGenre(Long id);
    GenreDTO getGenreById(Long id);
    GenreDTO getGenreByName(String name);
    List<GenreDTO> getAllGenres();
    List<GenreDTO> getGenresByPage(int pageNumber, int pageSize);
    List<GetGenreWithBooksDTO> getGenresWithBooks();
    GetGenreWithBooksDTO getGenreBooksById(Long genreId);
}
