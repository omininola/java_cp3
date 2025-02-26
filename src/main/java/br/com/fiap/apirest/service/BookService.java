package br.com.fiap.apirest.service;

import br.com.fiap.apirest.dto.BookReq;
import br.com.fiap.apirest.dto.BookRes;
import br.com.fiap.apirest.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return new BookRes(
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getCategory().name(),
                book.getIsbn()
        );
    }

    public List<BookRes> booksToRes (List<Book> books) {
        return books.stream().map(this::bookToRes).collect(Collectors.toList());
    }
}
