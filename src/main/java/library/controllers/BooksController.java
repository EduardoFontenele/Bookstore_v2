package library.controllers;

import library.dtos.BookDTOWithoutID;
import library.dtos.BookDTOWithID;
import library.exceptions.NotFoundException;
import library.services.BookService;
import library.services.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BooksController {

    private final BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<BookDTOWithID> saveNewBook(@Validated @RequestBody BookDTOWithoutID inputDto) {
        return new ResponseEntity<>(bookService.saveNewBook(inputDto), HttpStatus.CREATED);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<BookDTOWithID>> listBooks() {
        return new ResponseEntity<>(bookService.listBooks(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<BookDTOWithoutID> getBookById(@PathVariable("id") Long id) {
        if(id == null || id <= 0) new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(bookService.getBookById(id).orElseThrow(NotFoundException::new));
    }
}
