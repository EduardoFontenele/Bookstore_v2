package library.mappers;

import library.dtos.BookDtoWithoutID;
import library.dtos.BookDtoWithID;
import library.entities.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final ModelMapper modelMapper;

    public BookDtoWithID entityToBookDtoWithID(Book entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, BookDtoWithID.class);
    }

    public BookDtoWithoutID entityToBookDtoWithoutID(Book entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, BookDtoWithoutID.class);
    }

    public BookDtoWithID partialBookDtoToComplete(BookDtoWithoutID dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, BookDtoWithID.class);
    }

    public BookDtoWithoutID completeBookDtoToPartial(BookDtoWithID dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, BookDtoWithoutID.class);
    }

    public Book completeBookDtoToEntity(BookDtoWithID dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Book.class);
    }

    public Book partialBookDtoToEntity(BookDtoWithoutID dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Book.class);
    }
}
