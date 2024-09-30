package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Publisher;
import com.tarkhan.backend.exception.BookWaveApiException;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.mapping.PublisherMapping;
import com.tarkhan.backend.model.publisher.CreatePublisherDTO;
import com.tarkhan.backend.model.publisher.GetPublisherWithBooksDTO;
import com.tarkhan.backend.model.publisher.PublisherDTO;
import com.tarkhan.backend.model.publisher.UpdatePublisherDTO;
import com.tarkhan.backend.repository.PublisherRepository;
import com.tarkhan.backend.service.PublisherService;
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
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapping publisherMapping;
    private final ModelMapper modelMapper;

    @Override
    public void createPublisher(CreatePublisherDTO createPublisherDTO) {
        Publisher publisher = modelMapper.map(createPublisherDTO, Publisher.class);

        boolean existName = publisherRepository.existsByName(createPublisherDTO.getName());
        if (existName) {
            throw new BookWaveApiException("Publisher already exists");
        }
        publisherRepository.save(publisher);
    }

    @Override
    public void updatePublisher(Long id, UpdatePublisherDTO updatePublisherDTO) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", "ID", id));

        boolean existName = publisherRepository.existsByName(updatePublisherDTO.getName());
        if (existName) {
            throw new BookWaveApiException("Publisher already exists");
        }

        modelMapper.map(updatePublisherDTO, publisher);
        publisherRepository.save(publisher);
    }

    @Override
    public void deletePublisher(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", "ID", id));
        publisherRepository.delete(publisher);
    }

    @Override
    public PublisherDTO getPublisherById(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", "ID", id));

        return modelMapper.map(publisher, PublisherDTO.class);
    }

    @Override
    public PublisherDTO getPublisherByName(String name) {
        Publisher publisher = publisherRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", "NAME", name));
        return modelMapper.map(publisher, PublisherDTO.class);
    }

    @Override
    public List<PublisherDTO> getAllPublishers() {
        List<Publisher> publishers = publisherRepository.findAll();
        return publishers.stream()
                .map(publisher -> modelMapper
                        .map(publisher, PublisherDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PublisherDTO> getPublishersByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Publisher> publishers = publisherRepository.findAll(pageable).getContent();
        return publishers.stream()
                .map(publisher -> modelMapper.map(publisher, PublisherDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetPublisherWithBooksDTO> getPublishersWithBooks() {
        List<Publisher> publishers = publisherRepository.findAll();
        return publishers.stream()
                .map(publisherMapping::getPublisherWithBooksDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GetPublisherWithBooksDTO getPublisherBooksById(Long publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", "ID", publisherId));

        return publisherMapping.getPublisherWithBooksDTO(publisher);
    }
}
