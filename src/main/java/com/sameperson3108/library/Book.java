package com.sameperson3108.library;

import java.time.LocalDate;

public record Book(
        Long id,
        String title,
        String author,

        Integer userId,
        LocalDate loanDate,
        LocalDate returnDate,
        BookStatus bookStatus
){
}
