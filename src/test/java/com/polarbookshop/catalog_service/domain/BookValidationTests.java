package com.polarbookshop.catalog_service.domain;

import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTests {
    private static Validator validator;

    @BeforeAll // 클래스 내의 테스트를 실행하기 전에 가장 먼저 실행할 코드 블록임을 나타낸다.
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test // 해당 메서드가 테스트 메서드임을 나타내는 애너테이션
    void whenAllFieldsCorrectThenValidationSucceds() {
        var book = new Book("1234567890", "Title", "Author", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty(); // AssertJ를 사용하여 유효성 검사 위반이 없음을 확인
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        var book = new Book("a234567890", "Title", "Author", 9.90); // 유효하지 않은 ISBN 코드로 책을 생성한다.
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1); // AssertJ를 사용하여 유효성 검사 위반이 하나 있음을 확인
        assertThat(violations.iterator().next().getMessage()).isEqualTo("The ISBN format must be valid."); // 유효성 검자 제약
                                                                                                           // 조건 위반이 잘못된
                                                                                                           // ISBN에 대한
                                                                                                           // 것인지 확인.
    }
}
