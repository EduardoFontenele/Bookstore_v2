package library.dtos;

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
public class BookDTOWithID {
    private Long id;
    private String title;
    private BigDecimal price;
    private Genre genre;
}
