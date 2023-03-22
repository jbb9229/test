package com.jbb.blogsearch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.blogsearch.data.Document;
import com.jbb.blogsearch.data.Meta;
import com.jbb.blogsearch.request.SearchRequest;
import com.jbb.blogsearch.response.SearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KakaoApiSearchService extends ApiSearchService {

    @Value("${jbb.search-url.kakao}")
    private String kakaoUrl;
    @Value("${jbb.key.kakao}")
    private String kakaoKey;

    public ResponseEntity<Map<String, Object>> search(SearchRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoKey);
        return getAPIData(request, restTemplate, headers, kakaoUrl);
    }

    public SearchResponse convertResponse(SearchRequest request, ResponseEntity<Map<String, Object>> response) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> body = response.getBody();
        List<Object> documentsData = (List<Object>) body.get("documents");
        List<Document> documents = new ArrayList<>();

        Meta meta = mapper.convertValue(body.get("meta"), Meta.class);
        for (Object document : documentsData) {
            documents.add(mapper.convertValue(document, Document.class));
        }

        return SearchResponse.builder().meta(meta).documents(documents).requestInfo(request).build();
    }

}
