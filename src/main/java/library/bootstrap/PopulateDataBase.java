package library.bootstrap;

import library.entities.Book;
import library.repositories.BookRepository;
import library.utils.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class PopulateDataBase implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String... args) {
        loadData();
    }

    private void loadData() {
        if(bookRepository.count() < 3) {
            Book book1 = new Book("Dom Casmurro", new BigDecimal("40"), Genre.ROMANCE);
            Book book2 = new Book("Homem-Aranha", new BigDecimal("40"), Genre.HQ);
            Book book3 = new Book("Eu Sou a Lenda", new BigDecimal("40"), Genre.FICCAO_CIENTIFICA);

            bookRepository.saveAll(Arrays.asList(book1, book2, book3));
        }
    }
}
