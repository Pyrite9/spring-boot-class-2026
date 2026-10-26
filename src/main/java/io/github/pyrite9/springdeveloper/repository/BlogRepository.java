package io.github.pyrite9.springdeveloper.repository;

import io.github.pyrite9.springdeveloper.dao.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
    // findById(), findAll(), save(), saveAll(), deleteById(), deleteAll()


}
