package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import progetto.GestoreLibreria;
import progetto.Libro;
import progetto.repository.JsonRepository;
import progetto.repository.Repository;
import progetto.repository.RepositoryFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {

    private Repository repository;
    private final GestoreLibreria gestore = GestoreLibreria.getInstance();

    @BeforeEach
    void setUp() {
        repository = RepositoryFactory.creaRepository("json");
    }

    @Test
    void testSalvaECaricaListaLibri() {
        Libro libro = new Libro("Test", "Autore", "123", "Genere", 4, Libro.StatoLettura.IN_LETTURA);
        repository.salva(List.of(libro));

        List<Libro> caricati = repository.carica();
        assertEquals(1, caricati.size());
        assertEquals("123", caricati.getFirst().getIsbn());
    }

    @Test
    void testSalvaSingoloLibroNonDuplicato() {
        Libro libro = new Libro("Titolo", "Autore", "ABC", "Genere", 3, Libro.StatoLettura.DA_LEGGERE);
        assertTrue(gestore.aggiungiLibro(libro));
        assertFalse(gestore.aggiungiLibro(libro)); // Tentativo di salvataggio duplicato
    }

    @Test
    void testFactoryRestituisceJsonRepository() {
        Repository repo = RepositoryFactory.creaRepository("json");
        assertNotNull(repo);
        assertInstanceOf(JsonRepository.class, repo);
        gestore.eliminaLibreria();
    }
}
