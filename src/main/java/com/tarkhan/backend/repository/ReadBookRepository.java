package com.tarkhan.backend.repository;

import com.tarkhan.backend.entity.ReadBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadBookRepository extends JpaRepository<ReadBook, Long> {
}
