package com.jbb.blogsearch.service;

import com.jbb.blogsearch.data.Document;
import com.jbb.blogsearch.data.Meta;
import com.jbb.blogsearch.data.SearchType;
import com.jbb.blogsearch.request.SearchRequest;
import com.jbb.blogsearch.response.SearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NaverApiSearchService extends ApiSearchService {

    @Value("${jbb.search-url.naver}")
    private String naverUrl;
    @Value("${jbb.key.naver.id}")
    private String naverId;
    @Value("${jbb.key.naver.secret}")
    private String naverSecret;

    public ResponseEntity<Map<String, Object>> search(SearchRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverId);
        headers.set("X-Naver-Client-Secret", naverSecret);
        return getAPIData(request, restTemplate, headers, naverUrl);
    }

    public SearchResponse convertResponse(SearchRequest request, ResponseEntity<Map<String, Object>> response) {
        request.setType(SearchType.NAVER);
        Map<String, Object> body = response.getBody();
        List<Map<String, Object>> documentsData = (List<Map<String, Object>>) body.get("items");
        List<Document> documents = new ArrayList<>();

        Meta meta = Meta.builder()
                        .isEnd((Integer) body.get("start") < (Integer) body.get("total") / (Integer) body.get("display"))
                        .totalCount((Integer) body.get("total"))
                        .pageableCount(1000 / request.getSize() > (Integer) body.get("total") / request.getSize() ? (Integer) body.get("total") / request.getSize() : 1000 / request.getSize())
                        .build();
        for (Map<String, Object> document : documentsData) {
            String documentDate = String.valueOf(document.get("postdate"));
            LocalDate date = LocalDate.of(Integer.parseInt(documentDate.substring(0, 4)), Integer.parseInt(documentDate.substring(4, 6)), Integer.parseInt(documentDate.substring(6, 8)));
            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.NOON);
            documents.add(Document.builder()
                                  .url(String.valueOf(document.get("link")))
                                  .blogname(String.valueOf(document.get("bloggername")))
                                  .contents(String.valueOf(document.get("description")))
                                  .datetime(ZonedDateTime.of(dateTime, ZoneId.of("Asia/Seoul")).toString().substring(0, 22))
                                  .title(String.valueOf(document.get("title")))
                                  .build());
        }

        return SearchResponse.builder().meta(meta).documents(documents).requestInfo(request).build();
    }

}
