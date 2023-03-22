package com.jbb.blogsearch.response;

import com.jbb.blogsearch.data.Document;
import com.jbb.blogsearch.data.Meta;
import com.jbb.blogsearch.request.SearchRequest;
import lombok.*;

import java.util.List;

@Getter
public class SearchResponse {

    private Meta meta;

    private List<Document> documents;

    private SearchRequest requestInfo;

    @Builder
    public SearchResponse(Meta meta, List<Document> documents, SearchRequest requestInfo) {
        this.meta = meta;
        this.documents = documents;
        this.requestInfo = requestInfo;
    }

}
