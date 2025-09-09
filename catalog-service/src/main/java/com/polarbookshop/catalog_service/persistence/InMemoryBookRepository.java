package com.polarbookshop.catalog_service.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookRepository;
import org.springframework.stereotype.Repository;

@Repository // 해당 클래스는 스프링이 관리하는 저장소라는 것을 표시하는 스테레오타입 애너테이션
public class InMemoryBookRepository implements BookRepository {
    private static final Map<String, Book> books = new ConcurrentHashMap<>(); // 테스트 목적으로 책을 저장하기 위한 인메모리 맵

    @Override
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return existsByIsbn(isbn) ? Optional.of(books.get(isbn)) : Optional.empty();
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return books.get(isbn) != null;
    }

    @Override
    public Book save(Book book) {
        books.put(book.isbn(), book);
        return book;
    }

    @Override
    public void deleteByIsbn(String isbn) {
        books.remove(isbn);
    }
}