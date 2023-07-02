package library.mappers;

import library.dtos.BookDTOWithoutID;
import library.dtos.BookDTOWithID;
import library.entities.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BasicMappers {

    private final ModelMapper modelMapper;

    public BookDTOWithID entityToBookDtoWithID(Book entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, BookDTOWithID.class);
    }

    public BookDTOWithoutID entityToBookDtoWithoutID(Book entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, BookDTOWithoutID.class);
    }

    public BookDTOWithID partialBookDtoToComplete(BookDTOWithoutID dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, BookDTOWithID.class);
    }

    public BookDTOWithoutID completeBookDtoToPartial(BookDTOWithID dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, BookDTOWithoutID.class);
    }

    public Book BookDtoWithIDToEntity(BookDTOWithoutID dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Book.class);
    }
}
