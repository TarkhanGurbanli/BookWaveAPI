package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.genre.CreateGenreDTO;
import com.tarkhan.backend.model.genre.GenreDTO;
import com.tarkhan.backend.model.genre.GetGenreWithBooksDTO;
import com.tarkhan.backend.model.genre.UpdateGenreDTO;
import com.tarkhan.backend.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @PostMapping(value = "/admin")
    @Operation(summary = "Create Genre", description = "Creates a new genre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Genre created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ResponseModel> createGenre(
            @Valid @RequestBody CreateGenreDTO dto
    ) {
        genreService.createGenre(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/admin/{id}")
    @Operation(summary = "Update Genre", description = "Updates an existing genre by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre updated successfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ResponseModel> updateGenre(
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody UpdateGenreDTO dto
    ) {
        genreService.updateGenre(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/admin/{id}")
    @Operation(summary = "Delete Genre", description = "Deletes a genre by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Genre deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    public ResponseEntity<ResponseModel> deleteGenre(
            @Valid @PathVariable("id") Long id
    ) {
        genreService.deleteGenre(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }

    @GetMapping
    @Operation(summary = "Get All Genres", description = "Retrieves a list of all genres.")
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<GenreDTO> genres = genreService.getAllGenres();
        return ResponseEntity.status(HttpStatus.OK).body(genres);
    }

    @GetMapping("/with-books")
    @Operation(summary = "Get Genres with Books",
            description = "Retrieves genres along with their associated books.")
    public ResponseEntity<List<GetGenreWithBooksDTO>> getGenresWithBooks() {
        List<GetGenreWithBooksDTO> genresWithBooks = genreService.getGenresWithBooks();
        return ResponseEntity.status(HttpStatus.OK).body(genresWithBooks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Genre by ID",
            description = "Retrieves a genre by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre found"),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    public ResponseEntity<GenreDTO> getGenreById(
            @Valid @PathVariable("id") Long id
    ) {
        GenreDTO genre = genreService.getGenreById(id);
        return ResponseEntity.status(HttpStatus.OK).body(genre);
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "Get Books by Genre ID",
            description = "Retrieves books associated with a specific genre.")
    public ResponseEntity<GetGenreWithBooksDTO> getGenreBooksById(
            @Valid @PathVariable("id") Long id
    ) {
        GetGenreWithBooksDTO genreWithBooks = genreService.getGenreBooksById(id);
        return ResponseEntity.status(HttpStatus.OK).body(genreWithBooks);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get Genre by Name",
            description = "Retrieves a genre by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre found"),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    public ResponseEntity<GenreDTO> getGenreByName(
            @Valid @PathVariable("name") String name
    ) {
        GenreDTO genre = genreService.getGenreByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(genre);
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    @Operation(summary = "Get Paginated Genres",
            description = "Retrieves a paginated list of genres.")
    public ResponseEntity<List<GenreDTO>> getGenresByPage(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize
    ) {
        List<GenreDTO> genres = genreService.getGenresByPage(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(genres);
    }
}
