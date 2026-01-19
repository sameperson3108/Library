package com.sameperson3108.library;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LibraryService {

    private final Map<Long, Book> booksMap;
    private final AtomicLong idCounter;

    public LibraryService() {
        booksMap = new HashMap<>();
        idCounter = new AtomicLong();
    }

    public Book getBookById(int id) {
        if (!booksMap.containsKey(id)) throw new NoSuchElementException("Book with id " + id + " not found");
        return booksMap.get(id);
    }

    public List<Book> getAllBooks() {
        return booksMap.values().stream().toList();
    }

    public Book createBook(Book bookToCreate) {
        if (bookToCreate.id() != null) throw new IllegalArgumentException("id should be empty");
        if (bookToCreate.bookStatus() != null) throw new IllegalArgumentException("bookStatus should be empty");

        var newBook = new Book(
                idCounter.incrementAndGet(),
                bookToCreate.title(),
                bookToCreate.author(),
                bookToCreate.userId(),
                bookToCreate.loanDate(),
                bookToCreate.returnDate(),
                BookStatus.FREE
        );
        booksMap.put(newBook.id(), newBook);
        return newBook;
    }
}
