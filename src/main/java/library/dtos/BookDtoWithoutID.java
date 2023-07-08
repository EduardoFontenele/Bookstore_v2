package library.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import library.utils.Genre;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDtoWithoutID {

    @NotEmpty(message = "Field must not be empty")
    @Size(min = 3, max = 255, message = "Field must have 3-255 characters")
    private String title;

    @NotNull(message = "Price must not be null")
    private BigDecimal price;

    @NotNull(message = "Genre must not be null")
    private Genre genre;
}
