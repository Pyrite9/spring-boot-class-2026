package io.github.pyrite9.springdeveloper.controller;

import io.github.pyrite9.springdeveloper.dao.Article;
import io.github.pyrite9.springdeveloper.dto.ArticleCreationRequest;
import io.github.pyrite9.springdeveloper.dto.ArticleResponse;
import io.github.pyrite9.springdeveloper.dto.ArticleUpdateRequest;
import io.github.pyrite9.springdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {
    private final BlogService blogService;  // injected by constructor, never `new`

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleCreationRequest articleCreationRequest) {
        Article savedArticle = blogService.save(articleCreationRequest);
        // 201 Created + saved entity
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        /* same as:
        List<ArticleResponse> articleResponseList = new ArrayList<>();
        for (Article article : blogService.findAll()) {
            articleResponseList.add(new ArticleResponse(article));
        }
        */
        List<ArticleResponse> articleResponseList = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articleResponseList);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> getById(@PathVariable Long id) {
        Article article = blogService.getById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable Long id, @RequestBody ArticleUpdateRequest request) {
        Article article = blogService.updateArticle(id, request);
        ArticleResponse articleResponse = new ArticleResponse(article);
        return ResponseEntity.ok().body(articleResponse);
    }
}
