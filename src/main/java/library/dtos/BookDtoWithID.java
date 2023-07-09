package library.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import library.utils.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDtoWithID {

    @Min(value = 1, message = "Id can't be negative or zero")
    private Long id;

    @NotEmpty(message = "Field 'title' must not be empty")
    @Size(min = 3, max = 255, message = "Field must have 3-255 characters")
    private String title;

    @NotNull(message = "Price must not be null")
    @Min(value = 0, message = "Price can't be negative")
    private BigDecimal price;

    @NotNull(message = "Genre must not be null")
    private Genre genre;
}
