package com.jbb.blogsearch.service;

import com.jbb.blogsearch.data.SearchType;
import com.jbb.blogsearch.domain.SearchCount;
import com.jbb.blogsearch.repository.SearchCountRepository;
import com.jbb.blogsearch.request.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SearchCountService searchCountService;

    @Autowired
    private SearchCountRepository searchCountRepository;

    @BeforeEach
    void clean() {searchCountRepository.deleteAll();}

    @Test
    @DisplayName("Kakao 블로그 검색")
    void test_search_kakao_blog() {
        // given
        SearchRequest request = SearchRequest.builder().query("카카오 뱅크").build();

        // when
        searchService.search(request);

        // then
        List<SearchCount> searchCount = searchCountRepository.findAll();
        assertEquals(searchCount.size(), 1L);
        assertEquals(searchCount.get(0).getKeyword(), request.getQuery());
    }

    @Test
    @DisplayName("Naver 블로그 검색")
    void test_search_naver_blog() {
        // given
        SearchRequest request = SearchRequest.builder().query("카카오 뱅크").type(SearchType.NAVER).build();

        // when
        searchService.search(request);

        // then
        List<SearchCount> searchCount = searchCountRepository.findAll();
        assertEquals(searchCount.size(), 1L);
        assertEquals(searchCount.get(0).getKeyword(), request.getQuery());
    }

    @Test
    @DisplayName("Keyword 랭킹 테스트")
    void test_keyword_rank() {
        // given
        SearchRequest request = SearchRequest.builder().query("카카오 뱅크").build();
        for (int i = 0; i < 10; i++) {
            searchService.search(request);
        }
        request = SearchRequest.builder().query("카카오 페이").build();
        for (int i = 0; i < 9; i++) {
            searchService.search(request);
        }
        request = SearchRequest.builder().query("카카오 톡").build();
        for (int i = 0; i < 8; i++) {
            searchService.search(request);
        }
        request = SearchRequest.builder().query("카카오 모빌리티").build();
        for (int i = 0; i < 7; i++) {
            searchService.search(request);
        }
        request = SearchRequest.builder().query("카카오 쇼핑").build();
        for (int i = 0; i < 6; i++) {
            searchService.search(request);
        }
        request = SearchRequest.builder().query("카카오 게임즈").build();
        for (int i = 0; i < 5; i++) {
            searchService.search(request);
        }

        // when
        List<SearchCount> searchCounts = searchCountService.getKeywordRank();

        // then
        assertEquals(searchCounts.size(), 6);
        assertEquals(searchCounts.get(0).getKeyword(), "카카오 뱅크");
        assertEquals(searchCounts.get(1).getKeyword(), "카카오 페이");
        assertEquals(searchCounts.get(2).getKeyword(), "카카오 톡");
        assertEquals(searchCounts.get(3).getKeyword(), "카카오 모빌리티");
        assertEquals(searchCounts.get(4).getKeyword(), "카카오 쇼핑");
        assertEquals(searchCounts.get(5).getKeyword(), "카카오 게임즈");
    }

}