package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import progetto.Libro;
import progetto.ordina.Ordina;
import progetto.ordina.OrdinaPerAutore;
import progetto.ordina.OrdinaPerTitolo;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrdinaTest {

    private Libro libro1;
    private Libro libro2;
    private Libro libro3;

    @BeforeEach
    void setUp() {
        libro1 = new Libro("Il Nome della Rosa", "Eco", "123", "Romanzo", 4, Libro.StatoLettura.LETTO);
        libro2 = new Libro("La Poetica", "Aristotele", "456", "Saggio", 5, Libro.StatoLettura.IN_LETTURA);
        libro3 = new Libro("Harry Potter", "Rowling", "789", "Fantasy", 3, Libro.StatoLettura.DA_LEGGERE);
    }


    @Test
    void testOrdinaPerTitolo() {
        Ordina ordinaTitolo = new OrdinaPerTitolo();
        List<Libro> ordinati = ordinaTitolo.ordina(List.of(libro1, libro2, libro3));

        assertEquals("Harry Potter", ordinati.get(0).getTitolo());
        assertEquals("Il Nome della Rosa", ordinati.get(1).getTitolo());
        assertEquals("La Poetica", ordinati.get(2).getTitolo());
    }

    @Test
    void testOrdinaPerAutore() {
        Ordina ordinaAutore = new OrdinaPerAutore();
        List<Libro> ordinati = ordinaAutore.ordina(List.of(libro1, libro2, libro3));

        assertEquals("Aristotele", ordinati.get(0).getAutore());
        assertEquals("Eco", ordinati.get(1).getAutore());
        assertEquals("Rowling", ordinati.get(2).getAutore());
    }
} 
