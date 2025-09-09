package com.polarbookshop.catalog_service.web;

import com.polarbookshop.catalog_service.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;

@JsonTest // JSON 직렬화 및 역직렬화 테스트임을 나타내는 애너테이션
class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json; // JSON 직렬화 및 역직렬화를 테스트하기 위한 유틸리티 클래스

    @Test
    void testSerialize() throws Exception {
        var book = new Book("1234567890", "Title", "Author", 9.90);
        var jsonContent = json.write(book); // Book 객체를 JSON으로 직렬화
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")// AssertJ를 사용하여 JSON 내용이 예상과 일치
                .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());
    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
                {
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 9.90
                }
                """; // JSON 문자열
        assertThat(json.parse(content))
                .usingRecursiveComparison() // JSON 문자열을 Book 객체로 역직렬화하고 예상 Book 객체와 재귀적으로 비교
                .isEqualTo(new Book("1234567890", "Title", "Author", 9.90));
    }
}