package library.services;

import library.dtos.BookDtoWithID;
import library.dtos.BookDtoWithoutID;

import java.util.List;

public interface BookService {

    BookDtoWithID saveNewBook(BookDtoWithoutID dto);
    List<BookDtoWithID> listBooks();
    BookDtoWithoutID getBookById(Long id);
}
