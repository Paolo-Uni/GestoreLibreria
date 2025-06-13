package progetto.filtri;

import progetto.Libro;

public class FiltroPerStato implements Filtro {

    private final Libro.StatoLettura stato;

    public FiltroPerStato(Libro.StatoLettura stato) {
        this.stato = stato;
    }

    @Override
    public boolean verifica(Libro libro) {
        return libro.getStatoLettura() == stato;
    }

}
