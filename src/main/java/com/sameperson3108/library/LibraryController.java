package com.sameperson3108.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") int id) {
        log.info("getBookById");
        return libraryService.getBookById(id);
    }

    @GetMapping()
    public List<Book> getAllBooks() {
        log.info("getAllBooks");
        return libraryService.getAllBooks();
    }

    @PostMapping()
    public Book createBook(@RequestBody Book bookToCreate) {
        log.info("createBook");
        return libraryService.createBook(bookToCreate);
    }
}
