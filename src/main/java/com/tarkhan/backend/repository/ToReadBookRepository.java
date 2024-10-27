package com.tarkhan.backend.repository;

import com.tarkhan.backend.entity.ToReadBook;
import com.tarkhan.backend.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToReadBookRepository extends JpaRepository<ToReadBook, Long> {
    List<ToReadBook> findAllByUser(User user);
    List<ToReadBook> findAllByUser(User user, Pageable pageable);
}
