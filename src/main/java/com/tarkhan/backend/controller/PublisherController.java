package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.publisher.CreatePublisherDTO;
import com.tarkhan.backend.model.publisher.GetPublisherWithBooksDTO;
import com.tarkhan.backend.model.publisher.PublisherDTO;
import com.tarkhan.backend.model.publisher.UpdatePublisherDTO;
import com.tarkhan.backend.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
@Validated
@Tag(name = "Publisher Management", description = "APIs for managing publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @PostMapping(value = "/admin")
    @Operation(summary = "Create a new publisher",
            description = "This endpoint creates a new publisher in the system.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201",
                            description = "Publisher created successfully",
                            content = @Content(schema = @Schema(implementation = ResponseModel.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                            description = "Invalid input",
                            content = @Content)
            })
    public ResponseEntity<ResponseModel> createPublisher(
            @Valid @RequestBody CreatePublisherDTO dto
    ) {
        publisherService.createPublisher(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/admin/{id}")
    @Operation(summary = "Update an existing publisher",
            description = "This endpoint updates the details of an existing publisher identified by ID.",
            parameters = @Parameter(name = "id", description = "ID of the publisher to be updated", required = true),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                            description = "Publisher updated successfully",
                            content = @Content(schema = @Schema(implementation = ResponseModel.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                            description = "Publisher not found",
                            content = @Content)
            })
    public ResponseEntity<ResponseModel> updatePublisher(
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody UpdatePublisherDTO dto
    ) {
        publisherService.updatePublisher(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/admin/{id}")
    @Operation(summary = "Delete a publisher",
            description = "This endpoint deletes a publisher identified by ID from the system.",
            parameters = @Parameter(name = "id", description = "ID of the publisher to be deleted", required = true),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204",
                            description = "Publisher deleted successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                            description = "Publisher not found",
                            content = @Content)
            })
    public ResponseEntity<ResponseModel> deletePublisher(
            @Valid @PathVariable("id") Long id
    ) {
        publisherService.deletePublisher(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }

    @GetMapping
    @Operation(summary = "Get all publishers",
            description = "This endpoint retrieves a list of all publishers in the system.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                            description = "List of publishers retrieved successfully",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = PublisherDTO.class)))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                            description = "No publishers found",
                            content = @Content)
            })
    public ResponseEntity<List<PublisherDTO>> getAllPublishers() {
        List<PublisherDTO> publishers = publisherService.getAllPublishers();
        return ResponseEntity.status(HttpStatus.OK).body(publishers);
    }

    @GetMapping("/with-books")
    @Operation(summary = "Get publishers with books",
            description = "This endpoint retrieves a list of publishers along with their associated books.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                            description = "List of publishers with books retrieved successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = GetPublisherWithBooksDTO.class)))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                            description = "No publishers found",
                            content = @Content)
            })
    public ResponseEntity<List<GetPublisherWithBooksDTO>> getPublishersWithBooks() {
        List<GetPublisherWithBooksDTO> publishers = publisherService.getPublishersWithBooks();
        return ResponseEntity.status(HttpStatus.OK).body(publishers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get publisher by ID",
            description = "This endpoint retrieves a publisher identified by its ID.",
            parameters = @Parameter(name = "id", description = "ID of the publisher to be retrieved", required = true),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                            description = "Publisher retrieved successfully",
                            content = @Content(schema = @Schema(implementation = PublisherDTO.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                            description = "Publisher not found",
                            content = @Content)
            })
    public ResponseEntity<PublisherDTO> getPublisherById(@Valid @PathVariable("id") Long id) {
        PublisherDTO publisher = publisherService.getPublisherById(id);
        return ResponseEntity.status(HttpStatus.OK).body(publisher);
    }

    @GetMapping("/{publisherId}/books")
    @Operation(summary = "Get books by publisher ID",
            description = "This endpoint retrieves books associated with a publisher identified by its ID.",
            parameters = @Parameter(name = "publisherId",
                    description = "ID of the publisher whose books are to be retrieved", required = true),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                            description = "Books retrieved successfully",
                            content = @Content(schema = @Schema(implementation = GetPublisherWithBooksDTO.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                            description = "Publisher not found",
                            content = @Content)
            })
    public ResponseEntity<GetPublisherWithBooksDTO> getPublisherBooksById(
            @Valid @PathVariable("publisherId") Long publisherId
    ) {
        GetPublisherWithBooksDTO publisher = publisherService.getPublisherBooksById(publisherId);
        return ResponseEntity.status(HttpStatus.OK).body(publisher);
    }

    @GetMapping("/name")
    @Operation(summary = "Get publisher by name",
            description = "This endpoint retrieves a publisher based on its name.",
            parameters = @Parameter(name = "publisherName",
                    description = "Name of the publisher to be retrieved", required = true),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                            description = "Publisher retrieved successfully",
                            content = @Content(schema = @Schema(implementation = PublisherDTO.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                            description = "Publisher not found",
                            content = @Content)
            })
    public ResponseEntity<PublisherDTO> getPublisherByName(
            @Valid @RequestParam("publisherName") String publisherName
    ) {
        PublisherDTO publisher = publisherService.getPublisherByName(publisherName);
        return ResponseEntity.status(HttpStatus.OK).body(publisher);
    }

    @GetMapping("/page")
    @Operation(summary = "Get publishers by page",
            description = "This endpoint retrieves a paginated list of publishers.",
            parameters = {
                    @Parameter(name = "pageNumber", description = "Page number to retrieve", required = true),
                    @Parameter(name = "pageSize", description = "Number of publishers per page", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                            description = "List of publishers retrieved successfully",
                            content = @Content(array =
                            @ArraySchema(schema = @Schema(implementation = PublisherDTO.class)))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                            description = "No publishers found",
                            content = @Content)
            })
    public ResponseEntity<List<PublisherDTO>> getPublishersByPage(
            @Valid @RequestParam("pageNumber") int pageNumber,
            @Valid @RequestParam("pageSize") int pageSize
    ) {
        List<PublisherDTO> publishers = publisherService.getPublishersByPage(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(publishers);
    }
}
