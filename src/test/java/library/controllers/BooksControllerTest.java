package library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import library.dtos.BookDTOWithID;
import library.dtos.BookDTOWithoutID;
import library.entities.Book;
import library.mappers.BasicMappers;
import library.repositories.BookRepository;
import library.services.BookService;
import library.utils.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BooksController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BooksControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    @Autowired
    ModelMapper modelMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    private BookDTOWithID bookDtoWithId;
    private BookDTOWithoutID bookDTOWithoutID;

    private final List<BookDTOWithID> listOfDtosWithId = new ArrayList<>();

    @BeforeEach
    public void init() {
        bookDtoWithId = BookDTOWithID.builder()
                .id(1L)
                .title("O Pequeno Príncipe")
                .price(new BigDecimal("10"))
                .genre(Genre.AUTO_AJUDA)
                .build();

        bookDTOWithoutID = BookDTOWithoutID.builder()
                .title("O Senhor dos Anéis")
                .price(new BigDecimal("10"))
                .genre(Genre.RELIGIAO)
                .build();

        listOfDtosWithId.add(bookDtoWithId);
    }

    @Test
    @DisplayName("List all books method should return ok")
    void testListBooks() throws Exception {
        given(bookService.listBooks()).willReturn(listOfDtosWithId);

        mockMvc.perform(get("/api/books/listAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    @DisplayName("Get book by id method should return ok and json data")
    void testGetBookById() throws Exception {
        given(bookService.getBookById(any(Long.class))).willReturn(Optional.of(bookDTOWithoutID));

        mockMvc.perform(get("/api/books/getById/" + any(Long.class))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(bookDTOWithoutID.getTitle())))
                .andExpect(jsonPath("$.price", is(10)))
                .andExpect(jsonPath("$.genre", is(bookDTOWithoutID.getGenre().toString())));
    }

    @Test
    @DisplayName("Save new book should return created and json data")
    void testSaveNewBook() throws Exception {
        when(bookService.saveNewBook(bookDTOWithoutID)).thenReturn(bookDtoWithId);

        String responseContent = objectMapper.writeValueAsString(bookDtoWithId);
        System.out.println("\n" + responseContent);

        mockMvc.perform(post("/api/books/save").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(responseContent)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price", is(10)));
    }
}