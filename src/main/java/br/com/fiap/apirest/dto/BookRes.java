package br.com.fiap.apirest.dto;

public record BookRes(
        String title,
        String author,
        double price,
        String category,
        String isbn
) {
}
