package progetto;

import java.util.Objects;

public class Libro{

    private String titolo;
    private String autore;
    private String isbn;
    private String genere;
    private int valutazione;
    private StatoLettura statoLettura;

    public enum StatoLettura {LETTO, DA_LEGGERE, IN_LETTURA}

    public Libro() {}

    public Libro(String titolo, String autore, String isbn, String genere, int valutazione, StatoLettura statoLettura) {
        this.titolo = titolo;
        this.autore = autore;
        this.isbn = isbn;
        this.genere = genere;
        setValutazione(valutazione);
        this.statoLettura = statoLettura;
    }

    // Getter e Setter
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getValutazione() {
        return valutazione;
    }

    public void setValutazione(int valutazione) {
        if(valutazione < 1 || valutazione > 5)
            throw new IllegalArgumentException("La valutazione deve essere un numero compreso tra 1 e 5.");
        this.valutazione = valutazione;
    }

    public StatoLettura getStatoLettura() {
        return statoLettura;
    }

    public void setStatoLettura(StatoLettura statoLettura) {
        this.statoLettura = statoLettura;
    }

    // equals e hashCode
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null)
            return false;
        if(!(o instanceof Libro)) return false;
        Libro libro = (Libro) o;
        return isbn.equals(libro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Libro [titolo=");
        builder.append(titolo);
        builder.append(", autore=");
        builder.append(autore);
        builder.append(", isbn=");
        builder.append(isbn);
        builder.append(", genere=");
        builder.append(genere);
        builder.append(", valutazione=");
        builder.append(valutazione);
        builder.append(", statoLettura=");
        builder.append(statoLettura);
        builder.append("]");
        return builder.toString();
    }
}
