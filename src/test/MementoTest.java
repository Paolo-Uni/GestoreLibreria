package test;

import org.junit.jupiter.api.Test;
import progetto.Libro;
import progetto.memento.Memento;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MementoTest {

    @Test
    public void testMemento() {
        List<Libro> libri = new ArrayList<>();
        libri.add(new Libro("Titolo", "Autore", "123", "Genere", 5, Libro.StatoLettura.LETTO));

        Memento memento = new Memento(libri);

        List<Libro> ottenuti = memento.getLibri();

        assertEquals(1, ottenuti.size());
        assertEquals("Titolo", ottenuti.getFirst().getTitolo());
    }
}
