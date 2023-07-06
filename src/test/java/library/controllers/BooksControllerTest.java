package library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import library.dtos.BookDTOWithID;
import library.dtos.BookDTOWithoutID;
import library.repositories.BookRepository;
import library.services.BookService;
import library.utils.Genre;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BooksController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@Slf4j
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
    private BookDTOWithoutID bookDtoWithoutId;

    private final List<BookDTOWithID> listOfDtosWithId = new ArrayList<>();

    @BeforeEach
    public void init() {
        bookDtoWithId = BookDTOWithID.builder()
                .id(1L)
                .title("O Pequeno Príncipe")
                .price(new BigDecimal("10"))
                .genre(Genre.AUTO_AJUDA)
                .build();

        bookDtoWithoutId = BookDTOWithoutID.builder()
                .title("O Senhor dos Anéis")
                .price(new BigDecimal("10"))
                .genre(Genre.RELIGIAO)
                .build();

        listOfDtosWithId.add(bookDtoWithId);
    }

    @Test
    @DisplayName("List all books method should return OK HttpStatus and JSON data")
    void testListBooks() throws Exception {
        given(bookService.listBooks()).willReturn(listOfDtosWithId);

        mockMvc.perform(get("/api/books/listAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].title", is(bookDtoWithId.getTitle())))
                .andExpect(jsonPath("$[0].price", is(10)))
                .andExpect(jsonPath("$[0].genre", is(bookDtoWithId.getGenre().toString())));
    }

    @Test
    @DisplayName("Get book by id method should return OK HttpStatus and JSON data")
    void testGetBookById() throws Exception {
        given(bookService.getBookById(any(Long.class))).willReturn(bookDtoWithoutId);

        mockMvc.perform(get("/api/books/getById/" + any(Long.class))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(bookDtoWithoutId.getTitle())))
                .andExpect(jsonPath("$.price", is(10)))
                .andExpect(jsonPath("$.genre", is(bookDtoWithoutId.getGenre().toString())));
    }

    @Test
    @DisplayName("Save new book should return CREATED HttpStatus")
    @Rollback
    void testSaveNewBook() throws Exception {
        when(bookService.saveNewBook(bookDtoWithoutId)).thenReturn(bookDtoWithId);

        String responseContent = objectMapper.writeValueAsString(bookDtoWithId);
        System.out.println("\n" + responseContent);

        mockMvc.perform(post("/api/books/save").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(responseContent)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }
}