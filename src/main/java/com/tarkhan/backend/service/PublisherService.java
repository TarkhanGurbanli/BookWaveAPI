package com.tarkhan.backend.service;

import com.tarkhan.backend.model.publisher.CreatePublisherDTO;
import com.tarkhan.backend.model.publisher.GetPublisherWithBooksDTO;
import com.tarkhan.backend.model.publisher.PublisherDTO;
import com.tarkhan.backend.model.publisher.UpdatePublisherDTO;

import java.util.List;

public interface PublisherService {
    void createPublisher(CreatePublisherDTO createPublisherDTO);
    void  updatePublisher(Long id, UpdatePublisherDTO updatePublisherDTO);
    void deletePublisher(Long id);
    PublisherDTO getPublisherById(Long id);
    PublisherDTO getPublisherByName(String name);
    List<PublisherDTO> getAllPublishers();
    List<PublisherDTO> getPublishersByPage(int pageNumber, int pageSize);
    List<GetPublisherWithBooksDTO> getPublishersWithBooks();
    GetPublisherWithBooksDTO getPublisherBooksById(Long publisherId);
}
