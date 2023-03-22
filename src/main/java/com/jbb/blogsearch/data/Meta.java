package com.jbb.blogsearch.data;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

@Data
@NoArgsConstructor
public class Meta {

    @JsonSetter("is_end")
    private boolean isEnd;
    @JsonSetter("pageable_count")
    private int pageableCount;
    @JsonSetter("total_count")
    private int totalCount;

    @Builder
    public Meta(boolean isEnd, int pageableCount, int totalCount) {
        this.isEnd = isEnd;
        this.pageableCount = pageableCount;
        this.totalCount = totalCount;
    }

}
