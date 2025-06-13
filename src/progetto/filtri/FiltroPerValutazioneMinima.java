package progetto.filtri;

import progetto.Libro;

public class FiltroPerValutazioneMinima implements Filtro {

    private final int valutazioneMin;

    public FiltroPerValutazioneMinima(int valutazioneMin) {
        this.valutazioneMin = valutazioneMin;
    }

    @Override
    public boolean verifica(Libro libro) {
        return libro.getValutazione() >= valutazioneMin;
    }

}
