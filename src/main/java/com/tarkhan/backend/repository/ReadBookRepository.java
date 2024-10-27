package com.tarkhan.backend.repository;

import com.tarkhan.backend.entity.ReadBook;
import com.tarkhan.backend.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadBookRepository extends JpaRepository<ReadBook, Long> {
    List<ReadBook> findAllByUser(User user);
    List<ReadBook> findAllByUser(User user, Pageable pageable);
}
