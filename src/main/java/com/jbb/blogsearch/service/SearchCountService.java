package com.jbb.blogsearch.service;

import com.jbb.blogsearch.domain.SearchCount;
import com.jbb.blogsearch.repository.SearchCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchCountService {

    private final SearchCountRepository searchCountRepository;

    public List<SearchCount> getKeywordRank() {
        Pageable sortedByCountDesc = PageRequest.of(0, 10, Sort.by("count").descending());
        return searchCountRepository.findAll(sortedByCountDesc).getContent();
    }

}
