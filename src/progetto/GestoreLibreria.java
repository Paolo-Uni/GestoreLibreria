package progetto;

import progetto.filtri.Filtro;
import progetto.memento.Memento;
import progetto.ordina.Ordina;
import progetto.repository.Repository;
import progetto.repository.RepositoryFactory;

import java.util.*;

public class GestoreLibreria {

    private static GestoreLibreria instance;
    private List<Libro> libri;
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
            salvaLibri();
            return true;
        }
        return false;
    }

    public boolean rimuoviLibro(String isbn) {
        boolean rimosso = libri.removeIf(l -> l.getIsbn().equals(isbn));
        if (rimosso)
            salvaLibri();
        return rimosso;
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

    public List<Libro> filtraLibri(Filtro filtro) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro l : libri) {
            if (filtro.verifica(l)) {
                risultati.add(l);
            }
        }
        return risultati;
    }

    public List<Libro> filtraLibri(List<Libro> libri, Filtro filtro) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro l : libri) {
            if (filtro.verifica(l)) {
                risultati.add(l);
            }
        }
        return risultati;
    }

    public List<Libro> getTuttiLibri() {
        return new ArrayList<>(libri);
    }

    public List<Libro> getLibriOrdinati(Ordina strategia) {
        return strategia.ordina(getTuttiLibri());
    }

    public void eliminaLibreria(){
        libri = new ArrayList<>();
        salvaLibri();
    }

    public void stampaLibreria() {
        System.out.println("Elenco dei libri presenti in libreria:");
        for(Libro libro : libri)
            System.out.println(libro);
    }

    public Memento memento(){
        ArrayList<Libro> ret = new ArrayList<>();
        for(Libro libro : libri)
            ret.add(new Libro(libro.getTitolo(), libro.getAutore(), libro.getIsbn(), libro.getGenere(), libro.getValutazione(), libro.getStatoLettura()));
        return new Memento(ret);
    }

    public void setMemento(Memento m){
        if(m != null){
            eliminaLibreria();
            for(Libro libro : m.getLibri())
                aggiungiLibro(new Libro(libro.getTitolo(), libro.getAutore(), libro.getIsbn(), libro.getGenere(), libro.getValutazione(), libro.getStatoLettura()));
            salvaLibri();
        }
    }
}
