package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.publisher.CreatePublisherDTO;
import com.tarkhan.backend.model.publisher.GetPublisherWithBooksDTO;
import com.tarkhan.backend.model.publisher.PublisherDTO;
import com.tarkhan.backend.model.publisher.UpdatePublisherDTO;
import com.tarkhan.backend.service.PublisherService;
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
@Tag(name = "Publisher Management")
public class PublisherController {

    private final PublisherService publisherService;

    @PostMapping
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

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
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
    public ResponseEntity<List<PublisherDTO>> getAllPublishers() {
        List<PublisherDTO> publishers = publisherService.getAllPublishers();
        return ResponseEntity.status(HttpStatus.OK).body(publishers);
    }

    @GetMapping("/with-books")
    public ResponseEntity<List<GetPublisherWithBooksDTO>> getPublishersWithBooks() {
        List<GetPublisherWithBooksDTO> publishers = publisherService.getPublishersWithBooks();
        return ResponseEntity.status(HttpStatus.OK).body(publishers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getPublisherById(@Valid @PathVariable("id") Long id) {
        PublisherDTO publisher = publisherService.getPublisherById(id);
        return ResponseEntity.status(HttpStatus.OK).body(publisher);
    }

    @GetMapping("/{publisherId}/books")
    public ResponseEntity<GetPublisherWithBooksDTO> getPublisherBooksById(
            @Valid @PathVariable("publisherId") Long publisherId
    ) {
        GetPublisherWithBooksDTO publisher = publisherService.getPublisherBooksById(publisherId);
        return ResponseEntity.status(HttpStatus.OK).body(publisher);
    }

    @GetMapping("/name")
    public ResponseEntity<PublisherDTO> getPublisherByName(
            @Valid @RequestParam("publisherName") String publisherName
    ) {
        PublisherDTO publisher = publisherService.getPublisherByName(publisherName);
        return ResponseEntity.status(HttpStatus.OK).body(publisher);
    }

    @GetMapping("/page")
    public ResponseEntity<List<PublisherDTO>> getPublishersByPage(
            @Valid @RequestParam("pageNumber") int pageNumber,
            @Valid @RequestParam("pageSize") int pageSize
    ) {
        List<PublisherDTO> publishers = publisherService.getPublishersByPage(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(publishers);
    }
}
