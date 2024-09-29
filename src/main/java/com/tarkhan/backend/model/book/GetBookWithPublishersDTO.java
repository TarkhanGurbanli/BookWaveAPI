package com.tarkhan.backend.model.book;

import com.tarkhan.backend.model.publisher.PublisherDTO;
import lombok.Data;


@Data
public class GetBookWithPublishersDTO {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private Long imageId;
    private PublisherDTO publisher;
}
