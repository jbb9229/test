package com.jbb.blogsearch.controller;

import com.jbb.blogsearch.domain.SearchCount;
import com.jbb.blogsearch.request.SearchRequest;
import com.jbb.blogsearch.response.SearchResponse;
import com.jbb.blogsearch.service.SearchCountService;
import com.jbb.blogsearch.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final SearchCountService searchCountService;

    /**
     * TODO: 블로그 검색 서비스 API
     */
    @GetMapping("/blog/search")
    SearchResponse search(@Valid SearchRequest request) {
        return searchService.search(request);
    }

    @GetMapping("/blog/search/keyword/rank")
    List<SearchCount> keywordRank() {
        return searchCountService.getKeywordRank();
    }

}
