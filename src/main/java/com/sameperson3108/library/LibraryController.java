package com.sameperson3108.library;

import org.apache.tomcat.jni.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/book")
public class LibraryController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        log.info("getBookById");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(libraryService.getBookById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        log.info("getAllBooks");
        return ResponseEntity.ok(libraryService.getAllBooks());
    }

    @PostMapping()
    public ResponseEntity<Book> createBook(@RequestBody Book bookToCreate) {
        log.info("createBook");
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("test-header", "123")
                .body(libraryService.createBook(bookToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book bookToUpdate) {
        log.info("updateBook");
        var updated = libraryService.updateBook(id, bookToUpdate);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        try {
            log.info("deleteBook");
            libraryService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/{id}/take")
    public ResponseEntity<Book> takeBook(@PathVariable("id") Long id) {
        log.info("takeBook");
        var book = libraryService.takeBook(id);
        return ResponseEntity.ok(book);
    }
}
