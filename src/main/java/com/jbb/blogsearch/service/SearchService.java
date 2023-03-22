package com.jbb.blogsearch.service;

import com.jbb.blogsearch.domain.SearchCount;
import com.jbb.blogsearch.repository.SearchCountRepository;
import com.jbb.blogsearch.request.SearchRequest;
import com.jbb.blogsearch.response.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static com.jbb.blogsearch.data.SearchType.NAVER;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final KakaoApiSearchService kakaoApiSearchService;
    private final NaverApiSearchService naverApiSearchService;

    private final SearchCountRepository searchCountRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public SearchResponse search(SearchRequest request) {
        Optional<SearchCount> optionalCount = searchCountRepository.findByKeyword(request.getQuery());
        if (optionalCount.isPresent()) {
            optionalCount.get().plusCount();
        } else {
            searchCountRepository.save(SearchCount.builder().keyword(request.getQuery()).build());
        }
        ResponseEntity<Map<String, Object>> apiResult;
        SearchResponse response;
        switch (request.getType()) {
            case NAVER:
                apiResult = naverApiSearchService.search(request);
                response = naverApiSearchService.convertResponse(request, apiResult);
                break;
            default:
                apiResult =  kakaoApiSearchService.search(request);
                if (!apiResult.getStatusCode().is2xxSuccessful()) {
                    request.setType(NAVER);
                    apiResult = naverApiSearchService.search(request);
                    response = naverApiSearchService.convertResponse(request, apiResult);
                } else {
                    response = kakaoApiSearchService.convertResponse(request, apiResult);
                }
                break;
        }

        return response;
    }

}
