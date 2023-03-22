package com.jbb.blogsearch.data;

import lombok.Getter;

@Getter
public enum SearchType {

    KAKAO("query", "sort", "page", "size"),
    NAVER("query", "sort", "start", "display");

    private String query;
    private String sort;
    private String page;
    private String size;

    SearchType(String query, String sort, String page, String size) {
        this.query = query;
        this.sort = sort;
        this.page = page;
        this.size = size;
    }

}
