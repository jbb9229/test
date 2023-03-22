package com.jbb.blogsearch.repository;

import com.jbb.blogsearch.domain.SearchCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchCountRepository extends JpaRepository<SearchCount, Long> {

    Optional<SearchCount> findByKeyword(String keyword);

}
