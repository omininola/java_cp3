package br.com.fiap.apirest.dto;

import org.springframework.hateoas.Link;

public record BookRes(
        String title,
        String author,
        double price,
        String category,
        String isbn,
        Link link
) {
}
