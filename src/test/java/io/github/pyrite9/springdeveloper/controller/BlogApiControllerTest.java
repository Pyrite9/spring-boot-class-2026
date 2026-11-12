package io.github.pyrite9.springdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pyrite9.springdeveloper.dao.Article;
import io.github.pyrite9.springdeveloper.dto.ArticleCreationRequest;
import io.github.pyrite9.springdeveloper.dto.ArticleUpdateRequest;
import io.github.pyrite9.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogRepository blogRepository;

    @AfterEach
    void cleanUp() {
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle: create a blog article")
    @Test
    void addArticle() throws Exception {
        // given: request url and body
        String url = "/api/articles";
        String title = "title";
        String content = "content";
        ArticleCreationRequest request = new ArticleCreationRequest(title, content);
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then: 201 Created, and the article is stored in DB
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();
        assertThat(articles).hasSize(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("getAllArticles: read all blog articles")
    @Test
    void getAllArticles() throws Exception {
        // given: request url and saved article
        String url = "/api/articles";
        String title = "title";
        String content = "content";
        blogRepository.save(Article.builder().title(title).content(content).build());

        // when
        ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("getById: read a blog article by id")
    @Test
    void getById() throws Exception {
        // given
        String url = "/api/articles/{id}";
        String title = "title";
        String content = "content";
        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        // when
        ResultActions result = mockMvc.perform(get(url, savedArticle.getId()).accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @DisplayName("deleteArticle: delete a blog article by id")
    @Test
    void deleteArticle() throws Exception {
        // given
        String url = "/api/articles/{id}";
        Article savedArticle = blogRepository.save(Article.builder().title("title").content("content").build());

        // when
        ResultActions result = mockMvc.perform(delete(url, savedArticle.getId()));

        // then: 200 OK, and DB is empty
        result.andExpect(status().isOk());

        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("UpdateArticle : update blog article")
    @Test
    void updateArticle() throws Exception {
        // given
        String url = "/api/articles/{id}";
        String title = "title";
        String content = "content";
        Article savedArticle = blogRepository.save(Article.builder().title("title").content("content").build());

        String newTitle = "new title";
        String newContent = "new content";
        ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest(newTitle, newContent);
        String requestBody = objectMapper.writeValueAsString(articleUpdateRequest);
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        result.andExpect(status().isOk());
        Article article = blogRepository.findById(savedArticle.getId()).get();
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

    }
}
