package library.services;

import library.dtos.BookDtoWithID;
import library.dtos.BookDtoWithoutID;
import org.springframework.data.domain.Page;

public interface BookService {

    BookDtoWithID saveNewBook(BookDtoWithoutID dto);
    Page<BookDtoWithID> listBooks(Integer pageNumber, Integer pageSize);
    BookDtoWithoutID getBookById(Long id);
    void updateBook(BookDtoWithID dto);
    void deleteBookById(Long id);
}
