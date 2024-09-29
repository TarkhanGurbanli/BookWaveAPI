package com.tarkhan.backend.repository;

import com.tarkhan.backend.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    boolean existsByName(String name);
    Optional<Publisher> findByName(String name);
}
