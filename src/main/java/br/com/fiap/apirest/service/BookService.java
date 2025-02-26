package br.com.fiap.apirest.service;

import br.com.fiap.apirest.controller.BookController;
import br.com.fiap.apirest.dto.BookReq;
import br.com.fiap.apirest.dto.BookRes;
import br.com.fiap.apirest.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {
    public Book reqToBook (BookReq bookReq) {
        Book book = new Book();

        book.setTitle(bookReq.title());
        book.setAuthor(bookReq.author());
        book.setPrice(bookReq.price());
        book.setCategory(bookReq.category());
        book.setIsbn(bookReq.isbn());

        return book;
    }

    public BookRes bookToRes (Book book) {
        Link link = linkTo(methodOn(BookController.class).readBooks(0, 2)).withRel("Book List");
        return new BookRes(
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getCategory().name(),
                book.getIsbn(),
                link
        );
    }

    public BookRes bookToRes (Book book, Link link) {
        return new BookRes(
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getCategory().name(),
                book.getIsbn(),
                link
        );
    }

    public List<BookRes> booksToRes (List<Book> books) {
        return books.stream().map(this::bookToRes).collect(Collectors.toList());
    }

    public Page<BookRes> pageToRes(Page<Book> pageLivro) {
        return pageLivro.map(book -> bookToRes(book, linkTo(methodOn(BookController.class).read(book.getId())).withSelfRel()));
    }
}
