package progetto;

import progetto.repository.Repository;
import progetto.repository.RepositoryFactory;

import java.util.*;

public class GestoreLibreria {

    private static GestoreLibreria instance;
    private final List<Libro> libri;
    private final Repository repository;

    private GestoreLibreria() {
        repository = RepositoryFactory.creaRepository("json");
        libri = repository.carica();
    }

    public static GestoreLibreria getInstance() {
        if (instance == null) {
            instance = new GestoreLibreria();
        }
        return instance;
    }

    public void salvaLibri() {
        repository.salva(libri);
    }

    public boolean aggiungiLibro(Libro libro) {
        if (!libri.contains(libro)) {
            libri.add(libro);
            repository.salva(libro);
            return true;
        }
        return false;
    }

    public boolean rimuoviLibro(String isbn) {
        boolean rimosso = false;
        for (Libro libro : libri) {
            if (libro.getIsbn().equals(isbn)) {
                libri.remove(libro);
                rimosso = true;
            }
        }
        if (rimosso)
            repository.salva(libri);
        return rimosso;
    }


    public boolean modificaLibro(String stringa, Libro nuovoLibro) {
        boolean modificato = false;
        for (int i = 0; i < libri.size(); i++) {
            if (libri.get(i).getIsbn().equals(stringa) || libri.get(i).getTitolo().equals(stringa)) {
                libri.set(i, nuovoLibro);
                modificato = true;
                break;
            }
        }
        if(modificato){
            repository.salva(libri);
        }
        return modificato;
    }

    public List<Libro> cercaPerTitolo(String titolo) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro l : libri) {
            if (l.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                risultati.add(l);
            }
        }
        return risultati;
    }

    public List<Libro> cercaPerAutore(String autore) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro l : libri) {
            if (l.getAutore().toLowerCase().contains(autore.toLowerCase())) {
                risultati.add(l);
            }
        }
        return risultati;
    }

    public List<Libro> getTuttiLibri() {
        return new ArrayList<>(libri);
    }

    public void stampaLibreria() {
        System.out.println("Elenco dei libri presenti in libreria:");
        for(Libro libro : libri)
            System.out.println(libro);
    }

}
