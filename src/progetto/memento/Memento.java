package progetto.memento;

import progetto.Libro;

import java.util.List;

public class Memento {

    private final List<Libro> libri;

    public Memento(List<Libro> libri) {
        this.libri = libri;
    }

    public List<Libro> getLibri() {
        return libri;
    }
}
