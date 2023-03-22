package com.jbb.blogsearch.request;

import com.jbb.blogsearch.data.SearchType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class SearchRequest {

    @NotBlank(message = "검색어는 필수입니다.")
    private String query;
    @Pattern(regexp = "^accuracy|recency$", message = "정렬은 정확도순과 최신순 중 하나만 가능합니다.")
    private String sort;
    private int page = 1;
    private int size = 10;
    private SearchType type = SearchType.KAKAO;

    @Builder
    public SearchRequest(String query, String sort, int page, int size, SearchType type) {
        this.query = query;
        if (sort == null) {
            this.sort = "accuracy";
        } else {
            this.sort = sort;
        }
        if (page == 0) {
            this.page = 1;
        } else {
            this.page = page;
        }
        if (size == 0) {
            this.size = 10;
        } else {
            this.size = size;
        }
        if (type == null) {
            this.type = SearchType.KAKAO;
        } else {
            this.type = type;
        }
    }

    public String toQueryString() {
        String result = "";

        if (query != null && query.length() > 0) {
            result += type.getQuery() + "=" + query;
        }
        if (sort != null && sort.length() > 0) {
            result += "&";
            switch (type) {
                case NAVER:
                    result += type.getSort() + "=";
                    switch (sort) {
                        case "accuracy":
                            result += "sim";
                            break;
                        case "recency":
                            result += "date";
                            break;
                    }
                    break;
                default:
                    result += type.getSort() + "=" + sort;
            }
        }
        if (page > 0) {
            result += "&";
            switch (type) {
                case NAVER:
                    result += type.getPage() + "=" + page * size;
                    break;
                default:
                    result += type.getPage() + "=" + page;
                    break;
            }
        }
        if (size > 0) {
            result += "&" + type.getSize() + "=" + size;
        }

        return result;
    }

}
