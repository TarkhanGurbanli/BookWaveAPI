package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.genre.CreateGenreDTO;
import com.tarkhan.backend.model.genre.GenreDTO;
import com.tarkhan.backend.model.genre.UpdateGenreDTO;
import com.tarkhan.backend.service.GenreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
@Validated
@Tag(name = "Genre Management")
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<ResponseModel> create(
            @Valid @RequestBody CreateGenreDTO dto
            ){
        genreService.createGenre(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel> update(
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody UpdateGenreDTO dto
    ){
        genreService.updateGenre(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> delete(
            @Valid @PathVariable("id") Long id
    ){
        genreService.deleteGenre(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAll(){
        List<GenreDTO> genreDTOs = genreService.getAllGenres();
        return ResponseEntity.status(HttpStatus.OK).body(genreDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getById(
            @Valid @PathVariable("id") Long id
    ){
        GenreDTO genreDTO = genreService.getGenreById(id);
        return ResponseEntity.status(HttpStatus.OK).body(genreDTO);
    }

    @GetMapping("/name/{genre-name}")
    public ResponseEntity<GenreDTO> getByGenreName(
            @Valid @PathVariable("genre-name") String genreName
    ){
        GenreDTO genreDTO = genreService.getGenreByName(genreName);
        return ResponseEntity.status(HttpStatus.OK).body(genreDTO);
    }

    @GetMapping("/page")
    public ResponseEntity<List<GenreDTO>> getGenresByPage(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize
    ){
        List<GenreDTO> genres = genreService.getPageAllGenres(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(genres);
    }
}
