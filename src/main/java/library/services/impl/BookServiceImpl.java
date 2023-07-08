package library.services.impl;

import library.dtos.BookDtoWithoutID;
import library.dtos.BookDtoWithID;
import library.entities.Book;
import library.exceptions.ApiException;
import library.exceptions.ErrorsTable;
import library.mappers.ProjectMapper;
import library.repositories.BookRepository;
import library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ProjectMapper projectMapper;

    @Override
    public BookDtoWithID saveNewBook(BookDtoWithoutID inputDto) {
        Book entity = projectMapper.partialBookDtoToEntity(inputDto);
        return projectMapper.entityToBookDtoWithID(bookRepository.save(entity));
    }

    @Override
    public List<BookDtoWithID> listBooks() {
        return bookRepository.findAll()
                .stream()
                .map(projectMapper::entityToBookDtoWithID)
                .toList();
    }

    @Override
    public BookDtoWithoutID getBookById(Long id) {
        Optional<Book> searchedBook = bookRepository.findById(id);
        if(searchedBook.isEmpty()) throw new ApiException(ErrorsTable.ELEMENT_NOT_FOUND, id.toString());
        return projectMapper.entityToBookDtoWithoutID(searchedBook.get());
    }
}
