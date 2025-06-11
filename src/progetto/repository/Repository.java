package progetto.repository;

import progetto.Libro;

import java.util.List;

public interface Repository {

    void salva(List<Libro> libri);

    void salva(Libro libro);

    List<Libro> carica();

}
