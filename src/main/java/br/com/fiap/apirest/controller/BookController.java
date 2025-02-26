package br.com.fiap.apirest.controller;

import br.com.fiap.apirest.dto.BookReq;
import br.com.fiap.apirest.dto.BookRes;
import br.com.fiap.apirest.model.Book;
import br.com.fiap.apirest.repository.BookRepository;
import br.com.fiap.apirest.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    // Create - POST
    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody BookReq bookReq) {
        Book savedBook = bookRepository.save(bookService.reqToBook(bookReq));
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Read - GET
    @GetMapping
    public ResponseEntity<Page<BookRes>> readLivros(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "2") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("title").ascending());
        Page<Book> pageLivros = bookRepository.findAll(pageable);
        Page<BookRes> listaLivrosRes = bookService.pageToRes(pageLivros);
        return new ResponseEntity<>(listaLivrosRes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookRes> read(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bookService.bookToRes(book.get()), HttpStatus.OK);
    }

    // Update - PUT
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@Valid @RequestBody BookReq bookReq, @PathVariable Long id) {
        Optional<Book> existingBook = bookRepository.findById(id);

        if (existingBook.isEmpty()) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        Book book = bookService.reqToBook(bookReq);
        book.setId(existingBook.get().getId());
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Delete - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        bookRepository.delete(book.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
