package io.github.pyrite9.springdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("GET /quiz?code=1 이면 응답 코드는 201, 응답 본문은 Created!를 리턴한다.")
    @Test
    void getQuiz1() throws Exception {
        // given
        final String url = "/quiz";

        // when
        final ResultActions result = mockMvc.perform(get(url).param("code", "1"));

        // then
        result.andExpect(status().isCreated()).andExpect(content().string("Created!"));

        result.andDo(print());
    }

    @DisplayName("GET /quiz?code=2 이면 응답 코드는 400, 응답 본문은 Bad Request! 를 리턴한다.")
    @Test
    void getQuiz2() throws Exception {
        // given
        final String url = "/quiz";

        // when
        final ResultActions result = mockMvc.perform(get(url).param("code", "2"));

        // then
        result.andExpect(status().isBadRequest()).andExpect(content().string("Bad Request!"));

        // print
        result.andDo(print());
    }

    @DisplayName("POST /quiz 요청, 요청 바디에 {'value':1} 이면 응답 코드는 403, 응답 본문은 Forbidden!")
    @Test
    public void postQuiz1() throws Exception {
        // ready given
        final String url = "/quiz";
        // launch when
        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Code(1))));
        // verification then
        result.andExpect(status().isForbidden()).andExpect(content().string("Forbidden!"));

    }

    @DisplayName("POST /quiz 요청, 요청 바디에 {'value':2} 이면 응답 코드는 200, 응답 본문은 OK!")
    @Test
    public void postQuiz2() throws Exception {
        // ready given
        final String url = "/quiz";
        // launch when
        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Code(2))));
        // verification then
        result.andExpect(status().isOk()).andExpect(content().string("OK!"));
    }
}