package com.tarkhan.backend.repository;

import com.tarkhan.backend.entity.ToReadBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToReadBookRepository extends JpaRepository<ToReadBook, Long> {
}
