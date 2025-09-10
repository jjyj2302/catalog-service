package com.polarbookshop.catalog_service.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record Book(

                @NotBlank(message = "The Book ISBN must be defiend.") @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.") String isbn,
                @NotBlank(message = "The Book title must be defined.") // 이 필드는 널 값이 되어서는 안 되고 화이트스페이스가 아닌 문자가 하나 이상 있어야
                                                                       // 한다.
                String title,

                @NotBlank(message = "The book author must be defined.") String author,

                @NotNull(message = "The book price must be defined.") @Positive( // 해당 필드는 널 값이 되어서는 안 되고 0보다 큰 값을 가져야
                                                                                 // 한다.
                                message = "The book price must be greater than zero.") Double price) {
}
