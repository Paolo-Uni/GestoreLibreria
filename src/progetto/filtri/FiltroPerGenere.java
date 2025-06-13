package progetto.filtri;

import progetto.Libro;

public class FiltroPerGenere implements Filtro {

    private final String genere;

    public FiltroPerGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public boolean verifica(Libro libro) {
        return libro.getGenere().equalsIgnoreCase(genere);
    }
}
