package library.mappers;

import library.dtos.BookDTOWithID;
import library.dtos.BookDTOWithoutID;
import library.entities.Book;
import library.utils.Genre;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectMapperTest {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProjectMapper projectMapper;

    private Book entity;
    private BookDTOWithID bookDtoWithId;
    private BookDTOWithoutID bookDtoWithoutId;
    @BeforeEach
    void setUp() {
        entity = new Book(1L, "Spider-Man", new BigDecimal("28.99"), Genre.HQ);
    }

    @Test
    @DisplayName("Should transform a entity into a output DTO")
    @Order(1)
    void testModelMapper() {
        bookDtoWithId = modelMapper.map(entity, BookDTOWithID.class);

        assertNotNull(bookDtoWithId);
        assertEquals(bookDtoWithId.getId(), entity.getId());
        assertNotEquals(bookDtoWithId.getClass(), entity.getClass());
    }

    @Test
    @DisplayName("Should use the mapper to transform the same entity into the same DTO")
    @Order(2)
    void testMapperEntityToDto() {
        bookDtoWithId = projectMapper.entityToBookDtoWithID(entity);

        assertNotNull(bookDtoWithId);
        assertEquals(bookDtoWithId.getId(), entity.getId());
        assertNotEquals(bookDtoWithId.getClass(), entity.getClass());
    }

    @Test
    @DisplayName("Should transform a DTO with ID field into one without ID filed")
    @Order(3)
    void testMapperCompleteBookDtoToPartial() {
        bookDtoWithId = projectMapper.entityToBookDtoWithID(entity);
        bookDtoWithoutId = projectMapper.completeBookDtoToPartial(bookDtoWithId);

        assertNotNull(bookDtoWithoutId);
        assertEquals(bookDtoWithId.getTitle(), bookDtoWithoutId.getTitle());
        assertEquals(bookDtoWithId.getPrice(), bookDtoWithoutId.getPrice());
        assertNotEquals(bookDtoWithId.getClass(), bookDtoWithoutId.getClass());
    }

    @Test
    @DisplayName("Should transform a DTO without ID field into one with ID filed")
    @Order(4)
    void testMapperPartialDtoToComplete() {
        bookDtoWithoutId = projectMapper.entityToBookDtoWithoutID(entity);
        bookDtoWithId = projectMapper.partialBookDtoToComplete(bookDtoWithoutId);

        assertNotNull(bookDtoWithId);
        assertEquals(bookDtoWithId.getTitle(), bookDtoWithoutId.getTitle());
        assertNull(bookDtoWithId.getId());
    }

    @Test
    @DisplayName("Should transform a Book DTO with ID field into a Book entity")
    @Order(4)
    void testCompleteBookDtoToEntity() {
        bookDtoWithId = projectMapper.entityToBookDtoWithID(entity);
        Book newEntity = projectMapper.completeBookDtoToEntity(bookDtoWithId);

        assertNotNull(newEntity);
        assertEquals(newEntity.getId(), bookDtoWithId.getId());
        assertEquals(newEntity.getTitle(), bookDtoWithId.getTitle());
    }

    @Test
    @DisplayName("Should transform a Book DTO without ID field into a Book entity")
    @Order(5)
    void testPartialBookDtoToEntity() {
        bookDtoWithoutId = projectMapper.entityToBookDtoWithoutID(entity);
        Book newEntity = projectMapper.partialBookDtoToEntity(bookDtoWithoutId);

        assertNotNull(newEntity);
        assertEquals(newEntity.getTitle(), bookDtoWithoutId.getTitle());
        assertEquals(newEntity.getClass(), Book.class);
    }
}