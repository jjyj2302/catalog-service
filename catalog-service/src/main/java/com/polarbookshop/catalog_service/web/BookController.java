package com.polarbookshop.catalog_service.web;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController // 클래스가 스프링 컴포넌트이고 REST 엔드포인트를 위한 핸들러를 제공한다는 것을 표시하는 스테레오타입 애너테이션
@RequestMapping("/books") // 클래스가 핸들러를 제공하는 루트 패스 URI("/books")를 인식
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping // HTTP GET 메서드를 특정 핸들러 메서드로 연결
    public Iterable<Book> get() {
        return bookService.viewBookList();
    }

    @GetMapping("/{isbn}")
    public Book getByIsbn(@PathVariable String isbn) { // @PathVariable 애너테이션은 메서드 매개변수를 URI 템플릿 변수 ({isbn})와 바인드한다.
        return bookService.viewBookDetails(isbn);
    }

    @PostMapping // HTTP POST 요청을 특정 핸들러 메서드로 연결
    @ResponseStatus(HttpStatus.CREATED) // 책이 성공적으로 생성되면 201 상태 코드를 반환한다.@GetMapping("path")
    public Book post(@Valid @RequestBody Book book) { // @RequestBod는 웹 요청의 본문을 메서드 변수르 바인드한다.
        return bookService.addBookToCatalog(book);
    }

    @DeleteMapping("/{isbn}") // HTTP DELETE 요청을 특정 핸들러 메서드로 연결
    @ResponseStatus(HttpStatus.NO_CONTENT) // 책이 성공적으로 삭제되면 204 상태 코드를 반환한다.")
    public void delete(@PathVariable String isbn) {
        bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("/{isbn}") // HTTP PUT 요청을 특정 핸들러 메서드로 연결
    public Book put(@PathVariable String isbn, @Valid @RequestBody Book book) {
        return bookService.editBookDetails(isbn, book);
    }

}
