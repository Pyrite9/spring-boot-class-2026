package io.github.pyrite9.springdeveloper.service;

import io.github.pyrite9.springdeveloper.dao.Article;
import io.github.pyrite9.springdeveloper.dto.ArticleCreationRequest;
import io.github.pyrite9.springdeveloper.dto.ArticleUpdateRequest;
import io.github.pyrite9.springdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(ArticleCreationRequest articleCreationRequest) {
        return blogRepository.save(articleCreationRequest.toEntity());
    }

    public Article getById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    @Transactional
    public Article updateArticle(Long id, ArticleUpdateRequest request) {
        Article article = getById(id);
        article.update(request.getTitle(), request.getContent());
        return article;
    }

    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }
}
