package library.dtos;

import jakarta.validation.constraints.*;
import library.utils.Genre;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDtoWithoutID {

    @NotEmpty(message = "Field 'title' must not be empty")
    @Size(min = 3, max = 255, message = "Field must have 3-255 characters")
    private String title;

    @NotNull(message = "Price must not be null")
    @Min(value = 0, message = "Price can't be negative")
    private BigDecimal price;

    @NotNull(message = "Genre must not be null")
    private Genre genre;
}
