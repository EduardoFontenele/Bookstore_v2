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
        if(bookRepository.count() < 10) {
            Book book1 = new Book("Dom Casmurro", new BigDecimal("40"), Genre.ROMANCE);
            Book book2 = new Book("Homem-Aranha", new BigDecimal("40"), Genre.HQ);
            Book book3 = new Book("Eu Sou a Lenda", new BigDecimal("40"), Genre.FICCAO_CIENTIFICA);
            Book book4 = new Book("Romeu e Julieta", new BigDecimal("35.60"), Genre.ROMANCE);
            Book book5 = new Book("Batman", new BigDecimal("16.30"), Genre.HQ);
            Book book6 = new Book("Duna", new BigDecimal("67.10"), Genre.FICCAO_CIENTIFICA);
            Book book7 = new Book("Pai Rico, Pai Pobre", new BigDecimal("67.10"), Genre.AUTO_AJUDA);
            Book book8 = new Book("A Culpa é das Estrelas", new BigDecimal("43.30"), Genre.ROMANCE);
            Book book9 = new Book("O Chamado do Ctchullu", new BigDecimal("13.96"), Genre.HQ);
            Book book10 = new Book("2001 - Uma Odisseia no Espaço", new BigDecimal("67.10"), Genre.FICCAO_CIENTIFICA);

            bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10));
        }
    }
}
