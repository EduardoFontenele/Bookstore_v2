package library.services;

import library.dtos.BookDTOWithID;
import library.dtos.BookDTOWithoutID;

import java.util.List;

public interface BookService {

    BookDTOWithID saveNewBook(BookDTOWithoutID dto);
    List<BookDTOWithID> listBooks();
    BookDTOWithoutID getBookById(Long id);
}
