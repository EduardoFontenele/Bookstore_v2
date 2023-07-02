package library.services.impl;

import library.dtos.BookDTOWithoutID;
import library.dtos.BookDTOWithID;
import library.entities.Book;
import library.mappers.BasicMappers;
import library.repositories.BookRepository;
import library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BasicMappers basicMappers;

    @Override
    public BookDTOWithID saveNewBook(BookDTOWithoutID inputDto) {
        Book entity = basicMappers.BookDtoWithIDToEntity(inputDto);
        return basicMappers.entityToBookDtoWithID(bookRepository.save(entity));
    }

    @Override
    public List<BookDTOWithID> listBooks() {
        return bookRepository.findAll()
                .stream()
                .map(basicMappers::entityToBookDtoWithID)
                .toList();
    }

    @Override
    public Optional<BookDTOWithoutID> getBookById(Long id) {
        return Optional.of(basicMappers.entityToBookDtoWithoutID(bookRepository.findById(id).get()));
    }
}
