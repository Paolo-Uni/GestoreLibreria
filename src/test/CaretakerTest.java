package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import progetto.Libro;
import progetto.memento.Caretaker;
import progetto.memento.Memento;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CaretakerTest {

    private Caretaker caretaker;
    private List<Libro> libri;

    @BeforeEach
    public void setUp() {
        caretaker = new Caretaker();
        libri = new ArrayList<>();
        libri.add(new Libro("Titolo", "Autore", "123", "Genere", 4, Libro.StatoLettura.DA_LEGGERE));
    }

    @Test
    public void testCaretaker() {
        Memento m = new Memento(libri);
        caretaker.addMemento(m);

        Memento ripristinato = caretaker.undo();
        assertNotNull(ripristinato);
        assertEquals("Titolo", ripristinato.getLibri().getFirst().getTitolo());
    }

    @Test
    public void testUndoOnEmptyReturnsNull() {
        Memento ripristinato = caretaker.undo();
        assertNull(ripristinato);
    }
}
