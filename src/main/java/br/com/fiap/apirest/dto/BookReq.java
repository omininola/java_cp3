package br.com.fiap.apirest.dto;

import br.com.fiap.apirest.model.Category;
import jakarta.validation.constraints.*;

public record BookReq(
        @NotBlank(message = "The title cannot be empty")
        @Size(min = 3, max = 254, message = "The title must be between 3 and 254 characters")
        String title,

        @NotBlank(message = "The author cannot be empty")
        @Size(min = 3, max = 150, message = "The author must be between 3 and 150 characters")
        String author,

        @Min(value = 1, message = "The price must be greater than 1")
        @Max(value = 1000, message = "The price must be less than 1000")
        double price,

        @NotNull(message = "The category cannot be empty")
        Category category,

        @Pattern(regexp = "^970(\\d{7}|\\d{10})$", message = "The ISBN number must start with 970 and have 10 or 13 digits")
        String isbn
) {
}
