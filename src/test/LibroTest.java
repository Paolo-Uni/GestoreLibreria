package test;

import org.junit.jupiter.api.Test;
import progetto.Libro;
import static org.junit.jupiter.api.Assertions.*;

public class LibroTest {

    @Test
    void testCostruttoreValoriCorretti() {
        Libro libro = new Libro("Il nome della rosa", "Eco", "123", "Romanzo", 4, Libro.StatoLettura.LETTO);

        assertEquals("Il nome della rosa", libro.getTitolo());
        assertEquals("Eco", libro.getAutore());
        assertEquals("123", libro.getIsbn());
        assertEquals("Romanzo", libro.getGenere());
        assertEquals(4, libro.getValutazione());
        assertEquals(Libro.StatoLettura.LETTO, libro.getStatoLettura());
    }

    @Test
    void testValutazioneLimiteSuperiore() {
        Libro libro = new Libro("Il nome della rosa", "Eco", "123", "Romanzo", 4, Libro.StatoLettura.LETTO);
        assertTrue(libro.getValutazione() <= 5);
    }

    @Test
    void testValutazioneLimiteInferiore() {
        Libro libro = new Libro("Il nome della rosa", "Eco", "123", "Romanzo", 4, Libro.StatoLettura.LETTO);
        assertTrue(libro.getValutazione() >= 1);
    }

    @Test
    void testEqualsConStessoISBN() {
        Libro l1 = new Libro("Libro A", "Autore A", "123", "Romanzo", 3, Libro.StatoLettura.IN_LETTURA);
        Libro l2 = new Libro("Libro B", "Autore B", "123", "Storico", 2, Libro.StatoLettura.DA_LEGGERE);
        assertEquals(l1, l2);
    }

    @Test
    void testToStringContieneTitolo() {
        Libro libro = new Libro("Il nome della rosa", "Eco", "123", "Romanzo", 4, Libro.StatoLettura.LETTO);
        assertTrue(libro.toString().contains("Il nome della rosa"));
    }
} 
