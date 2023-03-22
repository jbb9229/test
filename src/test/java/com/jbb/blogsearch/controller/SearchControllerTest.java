package com.jbb.blogsearch.controller;

import com.jbb.blogsearch.repository.SearchCountRepository;
import com.jbb.blogsearch.request.SearchRequest;
import com.jbb.blogsearch.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SearchCountRepository searchCountRepository;

    @Autowired
    private SearchService searchService;

    @BeforeEach
    void clean() {searchCountRepository.deleteAll();}

    @Test
    @DisplayName("Kakao 블로그 검색")
    void test_search_kakao_blog() throws Exception {
        // expected
        mockMvc.perform(get("/blog/search?query=카카오 뱅크")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta").isMap())
                .andExpect(jsonPath("$.documents").isArray())
                .andExpect(jsonPath("$.documents.length()", is(10)))
                .andExpect(jsonPath("$.requestInfo.query").value("카카오 뱅크"))
                .andExpect(jsonPath("$.requestInfo.type").value("KAKAO"))
                .andDo(print());
    }


    @Test
    @DisplayName("naver 블로그 검색")
    void test_search_naver_blog() throws Exception {
        // expected
        mockMvc.perform(get("/blog/search?query=카카오 뱅크&type=NAVER")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta").isMap())
                .andExpect(jsonPath("$.documents").isArray())
                .andExpect(jsonPath("$.documents.length()", is(10)))
                .andExpect(jsonPath("$.requestInfo.query").value("카카오 뱅크"))
                .andExpect(jsonPath("$.requestInfo.type").value("NAVER"))
                .andDo(print());
    }


    @Test
    @DisplayName("검색 키워드 랭킹")
    void test_keyword_rank() throws Exception {
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
        request = SearchRequest.builder().query("카카오 쇼핑").build();
        for (int i = 0; i < 7; i++) {
            searchService.search(request);
        }
        request = SearchRequest.builder().query("카카오 패션").build();
        for (int i = 0; i < 6; i++) {
            searchService.search(request);
        }

        // expected
        mockMvc.perform(get("/blog/search/keyword/rank")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$[0].keyword").value("카카오 뱅크"))
                .andExpect(jsonPath("$[0].count", is(10)))
                .andExpect(jsonPath("$[1].keyword").value("카카오 페이"))
                .andExpect(jsonPath("$[1].count", is(9)))
                .andExpect(jsonPath("$[2].keyword").value("카카오 톡"))
                .andExpect(jsonPath("$[2].count", is(8)))
                .andExpect(jsonPath("$[3].keyword").value("카카오 쇼핑"))
                .andExpect(jsonPath("$[3].count", is(7)))
                .andExpect(jsonPath("$[4].keyword").value("카카오 패션"))
                .andExpect(jsonPath("$[4].count", is(6)))
                .andDo(print());
    }

}