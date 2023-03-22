package com.jbb.blogsearch.data;

import lombok.*;

@Data
@NoArgsConstructor
public class Document {

    private String blogname;
    private String contents;
    private String datetime;
    private String thumbnail;
    private String title;
    private String url;

    @Builder
    public Document(String blogname, String contents, String datetime, String thumbnail, String title, String url) {
        this.blogname = blogname;
        this.contents = contents;
        this.datetime = datetime;
        this.thumbnail = thumbnail;
        this.title = title;
        this.url = url;
    }

}
