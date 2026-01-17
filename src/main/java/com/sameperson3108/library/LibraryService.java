package com.sameperson3108.library;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class LibraryService {

    private final Map<Integer, Book> booksMap = Map.of(
            1, new Book(1, "1984", "ORWELL", 1, LocalDate.now(), LocalDate.now().plusDays(10), BookStatus.TAKEN),
            2, new Book(2, "Hard to be God", "Strugackie", 2, LocalDate.now(), LocalDate.now().plusDays(5), BookStatus.FREE)
    );

    public Book getBookById(int id) {
        if (!booksMap.containsKey(id)) throw new NoSuchElementException("Book with id " + id + " not found");
        return booksMap.get(id);
    }

    public List<Book> getAllBooks() {
        return booksMap.values().stream().toList();
    }
}
