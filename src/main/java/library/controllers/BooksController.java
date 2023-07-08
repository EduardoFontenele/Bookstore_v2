package library.controllers;

import jakarta.validation.constraints.Min;
import library.dtos.BookDtoWithID;
import library.dtos.BookDtoWithoutID;
import library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<BookDtoWithID>> listBooks() {
        return new ResponseEntity<>(bookService.listBooks(), HttpStatus.OK);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<BookDtoWithoutID> getBookById(@Validated @Min(value = 1, message = "ID cannot be negative")
            @PathVariable("id") Long id) {
        if(id <= 0) throw new RuntimeException("ID cannot be negative");
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}
