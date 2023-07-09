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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<BookDtoWithID> listBooks(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        Page<Book> page;

        page = bookRepository.findAll(pageRequest);

        return page.map(projectMapper::entityToBookDtoWithID);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if(pageNumber == null || pageNumber <= 0) {
            queryPageNumber = 0;
        } else {
            queryPageNumber = pageNumber - 1;
        }

        if(pageSize == null || pageSize < 0) {
            queryPageSize = 5;
        } else {
            if(pageSize > 50) {
                queryPageSize = 50;
            } else {
                queryPageSize = pageSize;
            }
        }

        return PageRequest.of(queryPageNumber, queryPageSize);
    }

    @Override
    public BookDtoWithoutID getBookById(Long id) {
        Optional<Book> searchedBook = bookRepository.findById(id);
        if(searchedBook.isEmpty()) throw new ApiException(ErrorsTable.ELEMENT_NOT_FOUND, id.toString());
        return projectMapper.entityToBookDtoWithoutID(searchedBook.get());
    }


    @Override
    @Transactional
    public void updateBook(BookDtoWithID dto) {
        if(bookRepository.findById(dto.getId()).isEmpty()) throw new ApiException(ErrorsTable.ELEMENT_NOT_FOUND, dto.getId().toString());

        Book foundBook = bookRepository.findById(dto.getId()).get();
        foundBook.setTitle(dto.getTitle());
        foundBook.setPrice(dto.getPrice());
        foundBook.setGenre(dto.getGenre());
        bookRepository.save(foundBook);
    }

    @Override
    public void deleteBookById(Long id) {
        if(bookRepository.findById(id).isEmpty()) throw new ApiException(ErrorsTable.ELEMENT_NOT_FOUND, id.toString());
        bookRepository.deleteById(id);
    }
}
