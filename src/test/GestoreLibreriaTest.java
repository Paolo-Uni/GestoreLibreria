package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import progetto.GestoreLibreria;
import progetto.Libro;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestoreLibreriaTest {

    private GestoreLibreria gestore;
    private Libro libro1;
    private Libro libro2;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        GestoreLibreria gestore = GestoreLibreria.getInstance();
        gestore.eliminaLibreria();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        GestoreLibreria gestore = GestoreLibreria.getInstance();
        gestore.eliminaLibreria();
    }

    @BeforeEach
    void setUp() {
        gestore = GestoreLibreria.getInstance();
        gestore.eliminaLibreria();

        libro1 = new Libro("Il signore degli anelli", "Tolkien", "ISBN001", "Fantasy", 5, Libro.StatoLettura.LETTO);
        libro2 = new Libro("Il piccolo principe", "Saint-Exupéry", "ISBN002", "Narrativa", 4, Libro.StatoLettura.DA_LEGGERE);
    }

    @Test
    void testAggiungiLibro() {
        assertTrue(gestore.aggiungiLibro(libro1));
        assertFalse(gestore.aggiungiLibro(libro1)); // duplicato
        assertEquals(1, gestore.getTuttiLibri().size());
    }

    @Test
    void testRimuoviLibro() {
        gestore.aggiungiLibro(libro1);
        gestore.aggiungiLibro(libro2);
        gestore.rimuoviLibro(libro1.getIsbn());
        assertEquals(1, gestore.getTuttiLibri().size());
    }

    @Test
    void testCercaPerTitolo() {
        gestore.aggiungiLibro(libro1);
        List<Libro> risultati = gestore.cercaPerTitolo("signore");
        assertEquals(1, risultati.size());
        assertEquals("ISBN001", risultati.getFirst().getIsbn());
    }

    @Test
    void testCercaPerAutore() {
        gestore.aggiungiLibro(libro2);
        List<Libro> risultati = gestore.cercaPerAutore("Saint");
        assertEquals(1, risultati.size());
        assertEquals("ISBN002", risultati.getFirst().getIsbn());
    }
}
