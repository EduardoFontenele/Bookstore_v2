package library.controllers;

import jakarta.validation.constraints.Min;
import library.dtos.BookDtoWithID;
import library.dtos.BookDtoWithoutID;
import library.exceptions.ApiException;
import library.exceptions.ErrorsTable;
import library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books/")
public class BooksController {

    private final BookService bookService;

    @PostMapping("save")
    public ResponseEntity<BookDtoWithID> saveNewBook(@RequestBody @Validated BookDtoWithoutID inputDto) {
        return new ResponseEntity<>(bookService.saveNewBook(inputDto), HttpStatus.CREATED);
    }

    @GetMapping("getAll")
    public ResponseEntity<Page<BookDtoWithID>> listBooks(@RequestParam(required = false) Integer pageNumber,
                                                         @RequestParam(required = false) Integer pageSize) {
        return new ResponseEntity<>(bookService.listBooks(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<BookDtoWithoutID> getBookById(
            @Validated @Min(value = 1, message = "ID cannot be negative on path variable")
            @PathVariable("id") Long id) {
        if(id <= 0) throw new ApiException(ErrorsTable.CONSTRAINT_VIOLATED);
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("updateBook")
    public ResponseEntity<Void> updateBook(@Validated @RequestBody BookDtoWithID inputDto) {
        bookService.updateBook(inputDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("path", "/api/books/getById/" + inputDto.getId());
        return ResponseEntity.noContent().headers(headers).build();
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<Void> deleteBookById(
            @Validated @Min(value = 1, message = "ID cannot be negative on path variable")
            @PathVariable("id") Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }
}
