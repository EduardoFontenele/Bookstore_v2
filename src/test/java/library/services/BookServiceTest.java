package library.services;

import library.controllers.BooksController;
import library.dtos.BookDtoWithID;
import library.dtos.BookDtoWithoutID;
import library.entities.Book;
import library.mappers.ProjectMapper;
import library.repositories.BookRepository;
import library.utils.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Autowired
    BooksController booksController;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ProjectMapper projectMapper;

    private Book bookEntity;

    private BookDtoWithoutID bookDtoWithoutID;

    private BookDtoWithID bookDtoWithID;
    @BeforeEach
    void setUp() {
        bookEntity = new Book(1L, "Test", new BigDecimal("10"), Genre.ROMANCE);

        bookDtoWithoutID = BookDtoWithoutID.builder()
                .title(bookEntity.getTitle())
                .price(bookEntity.getPrice())
                .genre(bookEntity.getGenre())
                .build();

        bookDtoWithID = BookDtoWithID.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .price(bookEntity.getPrice())
                .genre(bookEntity.getGenre())
                .build();
    }

    @Test
    @DisplayName("Method 'saveNewBook' should persist data and return a DTO without id")
    void testSaveNewBook() {
        Book savedBook = bookRepository.save(bookEntity);
        bookDtoWithID = projectMapper.entityToBookDtoWithID(savedBook);

        assertNotNull(savedBook);
        assertEquals(bookEntity.getId(), savedBook.getId());
        assertEquals(bookEntity.getTitle(), savedBook.getTitle());
        assertEquals(bookEntity.getPrice(), savedBook.getPrice());
        assertEquals(bookEntity.getGenre(), savedBook.getGenre());

        assertEquals(savedBook.getId(), bookDtoWithID.getId());
        assertEquals(savedBook.getTitle(), bookDtoWithID.getTitle());
        assertEquals(savedBook.getPrice(), bookDtoWithID.getPrice());
        assertEquals(savedBook.getGenre(), bookDtoWithID.getGenre());
    }

    @Test
    @DisplayName("Method 'listBooks' should return list of DTOs with ID")
    void testListBooks() {
        List<BookDtoWithID> list = bookRepository.findAll()
                .stream()
                .map(projectMapper::entityToBookDtoWithID)
                .toList();

        list.forEach(dto -> System.out.println(dto.getTitle()));

        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertNotEquals(list.get(0), bookDtoWithID);

        for(BookDtoWithID dto : list) {
            assertEquals(dto.getClass(), BookDtoWithID.class, "DTOs should be instance of BookDTOWithID");
        }
    }

    @Test
    @DisplayName("Method 'getBookById' should return a Book and convert it to a DTO without ID")
    void testGetBookById() {
        bookDtoWithoutID = projectMapper.entityToBookDtoWithoutID(bookRepository.findById(1l).get());

        assertEquals(bookDtoWithoutID.getClass(), BookDtoWithoutID.class);
        assertNotNull(bookDtoWithoutID.getPrice());
        assertNotNull(bookDtoWithoutID.getTitle());
        assertNotNull(bookDtoWithoutID.getGenre());

    }
}