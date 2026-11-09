package io.github.pyrite9.springdeveloper.dto;

import io.github.pyrite9.springdeveloper.dao.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor      // for Jackson deserialization
@AllArgsConstructor     // for test code
public class ArticleCreationRequest {
    private String title;
    private String content;

    public Article toEntity() {
        // return new Article(this.title, this.content);
        return Article.builder().title(this.title).content(this.content).build();
    }
}
