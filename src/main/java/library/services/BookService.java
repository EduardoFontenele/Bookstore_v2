package library.services;

import library.dtos.BookDTOWithID;
import library.dtos.BookDTOWithoutID;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDTOWithID saveNewBook(BookDTOWithoutID dto);
    List<BookDTOWithID> listBooks();
    Optional<BookDTOWithoutID> getBookById(Long id);
}
