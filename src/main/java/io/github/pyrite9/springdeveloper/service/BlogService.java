package io.github.pyrite9.springdeveloper.service;

import io.github.pyrite9.springdeveloper.dao.Article;
import io.github.pyrite9.springdeveloper.dto.ArticleCreationRequest;
import io.github.pyrite9.springdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(ArticleCreationRequest articleCreationRequest) {
        return blogRepository.save(articleCreationRequest.toEntity());
    }
}
