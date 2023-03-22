package com.jbb.blogsearch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class SearchCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String keyword;
    private int count;

    @Builder
    public SearchCount(String keyword) {
        this.keyword = keyword;
        this.count = 1;
    }

    public void plusCount() {
        this.count++;
    }

}
