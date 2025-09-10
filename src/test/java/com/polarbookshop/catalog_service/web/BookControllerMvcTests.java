package com.polarbookshop.catalog_service.web;

import com.polarbookshop.catalog_service.domain.BookService.BookNotFoundException;
import com.polarbookshop.catalog_service.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class) // BookController에 대한 웹 계층 테스트임을 나타내는 애너테이션
class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc; // 모의 환경에서 웹 계층을 테스트하기 위한 유틸리티 클래스

    @MockBean
    private BookService bookService; // BookService의 모의 객체를 생성하여 BookController에 주입

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "7373737373713940";
        given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException.class); // 특정 ISBN에 대해
                                                                                         // BookNotFoundException을 던지도록
                                                                                         // 모의 설정
        mockMvc
                .perform(get("/books/{isbn}", isbn)) // 지정된 ISBN으로 GET 요청을 수행
                .andExpect(status().isNotFound()); // 응답 상태가 404 Not Found인지 확인
    }
}
