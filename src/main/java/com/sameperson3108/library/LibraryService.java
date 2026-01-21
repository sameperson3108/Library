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

    public Book getBookById(Long id) {
        //if (!booksMap.containsKey(id)) throw new NoSuchElementException("Book with id " + id + " not found");
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

    public Book updateBook(Long id, Book bookToUpdate) {
        if (!booksMap.containsKey(id)) throw new NoSuchElementException("Book with id " + id + " not found");
        var book = booksMap.get(id);
        if (book.bookStatus() != BookStatus.FREE) throw new IllegalArgumentException("bookStatus should be FREE");
        var updatedBook = new Book(
                book.id(),
                bookToUpdate.title(),
                bookToUpdate.author(),
                bookToUpdate.userId(),
                bookToUpdate.loanDate(),
                bookToUpdate.returnDate(),
                BookStatus.FREE
        );
        booksMap.put(book.id(), updatedBook);
        return updatedBook;
    }

    public void deleteBook(Long id) {
        if (!booksMap.containsKey(id)) throw new NoSuchElementException("Not found book with id " + id);
        booksMap.remove(id);
    }

    public Book takeBook(Long id) {
        if (!booksMap.containsKey(id)) throw new NoSuchElementException("Not found book with id " + id);
        var book = booksMap.get(id);
        if (book.bookStatus() != BookStatus.FREE) throw new IllegalArgumentException("bookStatus should be FREE");
        var isBookConflict = isBookConflict(book);

        if (isBookConflict) throw new IllegalArgumentException("bookStatus should be FREE");

        var takenBook = new Book(
                book.id(),
                book.title(),
                book.author(),
                book.userId(),
                book.loanDate(),
                book.returnDate(),
                BookStatus.TAKEN
        );
        booksMap.put(book.id(), takenBook);
        return takenBook;
    }

    private boolean isBookConflict(Book book) {
        for (Book existingBook : booksMap.values()) {
            if (book.id().equals(existingBook.id())) continue;
            if (!book.title().equals(existingBook.title())) continue;
            if (existingBook.bookStatus().equals(BookStatus.FREE)) continue;

            if (book.loanDate().isBefore(existingBook.returnDate()) && existingBook.loanDate().isBefore(book.returnDate())) return true;

        }
        return false;
    }
}
