package com.polarbookshop.catalog_service.web;

import java.util.HashMap;
import java.util.Map;
import com.polarbookshop.catalog_service.domain.BookService.BookAlreadyExistsException;
import com.polarbookshop.catalog_service.domain.BookService.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 해당 클래스가 모든 RestController에 적용되는 전역 예외 처리기임을 나타내는 애너테이션 (클래스가 중앙식 예외 핸들러임.)
public class BookControllerAdvice {

    @ExceptionHandler(BookNotFoundException.class) // 핸들러가 실행되어야 할 대상인 예외 정의
    @ResponseStatus(HttpStatus.NOT_FOUND) // HTTP 응답 상태 코드를 지정
    String bookNotFoundHandler(BookNotFoundException ex) {
        return ex.getMessage(); // Http 응답 본문에 포함할 메시지
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 예외를 발생시킬 때 HTTP 응답에 포함할 상태 코드 정의
    String bookAlreadyExistsHandler(BookAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage); // 빈 메시지 대신 의미 있는 오류 메시지를 위해 유효하지 않은 필드 확인
        });
        return errors; // 유효성 검사 오류에 대한 필드 이름과 오류 메시지를 맵으로 반환
    }
}
