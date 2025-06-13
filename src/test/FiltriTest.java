package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import progetto.GestoreLibreria;
import progetto.Libro;
import progetto.filtri.Filtro;
import progetto.filtri.FiltroPerGenere;
import progetto.filtri.FiltroPerStato;
import progetto.filtri.FiltroPerValutazioneMinima;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FiltriTest {

    private GestoreLibreria gestore;

    @BeforeEach
    void setUp() {
        gestore = GestoreLibreria.getInstance();

        Libro libro1 = new Libro("Libro Fantasy", "Autore A", "001", "Fantasy", 5, Libro.StatoLettura.LETTO);
        Libro libro2 = new Libro("Libro Storico", "Autore B", "002", "Storico", 3, Libro.StatoLettura.DA_LEGGERE);
        Libro libro3 = new Libro("Altro Libro", "Autore C", "003", "Fantasy", 4, Libro.StatoLettura.IN_LETTURA);

        gestore.aggiungiLibro(libro1);
        gestore.aggiungiLibro(libro2);
        gestore.aggiungiLibro(libro3);
    }

    @Test
    void testFiltroPerGenere() {
        Filtro filtro = new FiltroPerGenere("Fantasy");
        List<Libro> risultati = gestore.filtraLibri(filtro);
        assertEquals(2, risultati.size());
        assertTrue(risultati.stream().allMatch(l -> l.getGenere().equalsIgnoreCase("Fantasy")));
    }

    @Test
    void testFiltroPerStato() {
        Filtro filtro = new FiltroPerStato(Libro.StatoLettura.LETTO);
        List<Libro> risultati = gestore.filtraLibri(filtro);
        assertEquals(1, risultati.size());
        assertEquals("001", risultati.getFirst().getIsbn());
    }

    @Test
    void testFiltroPerValutazioneMinima() {
        Filtro filtro = new FiltroPerValutazioneMinima(4);
        List<Libro> risultati = gestore.filtraLibri(filtro);
        assertEquals(2, risultati.size());
        assertTrue(risultati.stream().allMatch(l -> l.getValutazione() >= 4));
        gestore.eliminaLibreria();
    }
}
