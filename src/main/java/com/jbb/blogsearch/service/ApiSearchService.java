package com.jbb.blogsearch.service;

import com.jbb.blogsearch.request.SearchRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public abstract class ApiSearchService {

    abstract ResponseEntity<Map<String, Object>> search(SearchRequest request);

    ResponseEntity<Map<String, Object>> getAPIData(SearchRequest request, RestTemplate restTemplate, HttpHeaders headers, String apiUrl) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                                                              .query(request.toQueryString());

        ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<>() {};

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, typeRef);

        return response;
    }

}
