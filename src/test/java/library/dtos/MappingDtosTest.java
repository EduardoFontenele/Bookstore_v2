package library.dtos;

import library.entities.Book;
import library.mappers.BasicMappers;
import library.utils.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MappingDtosTest {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BasicMappers basicMappers;

    @Test
    @DisplayName("Should transform a entity into a output DTO")
    @Order(1)
    void testModelMapper() {
        Book entity = new Book(1L, "Spider-Man", new BigDecimal("28.99"), Genre.HQ);
        BookDTOWithID dto = modelMapper.map(entity, BookDTOWithID.class);

        assertEquals(dto.getId(), entity.getId());
    }

    @Test
    @DisplayName("Should use the mapper to transform the same entity into the same DTO")
    @Order(2)
    void testMapperEntityToDto() {
        Book entity = new Book(1L, "Spider-Man", new BigDecimal("28.99"), Genre.HQ);
        BookDTOWithID dto = basicMappers.entityToBookDtoWithID(entity);

        assertEquals(dto.getId(), entity.getId());
    }

    @Test
    @DisplayName("Mapper should transform a DTO with id fiel into one without id filed")
    void testMapperCompleteBookDtoToPartial() {

    }
}