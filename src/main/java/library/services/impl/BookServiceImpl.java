package library.services.impl;

import library.dtos.BookDTOWithoutID;
import library.dtos.BookDTOWithID;
import library.entities.Book;
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
    public BookDTOWithID saveNewBook(BookDTOWithoutID inputDto) {
        Book entity = projectMapper.partialBookDtoToEntity(inputDto);
        return projectMapper.entityToBookDtoWithID(bookRepository.save(entity));
    }

    @Override
    public List<BookDTOWithID> listBooks() {
        return bookRepository.findAll()
                .stream()
                .map(projectMapper::entityToBookDtoWithID)
                .toList();
    }

    @Override
    public Optional<BookDTOWithoutID> getBookById(Long id) {
        return Optional.of(projectMapper.entityToBookDtoWithoutID(bookRepository.findById(id).get()));
    }
}
