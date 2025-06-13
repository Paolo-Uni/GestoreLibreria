package progetto.command;

import progetto.Libro;

public record ModificaCommand(Libro originale, Libro modificato) implements Command {

    @Override
    public void esegui() {
        originale.setTitolo(modificato.getTitolo());
        originale.setAutore(modificato.getAutore());
        originale.setIsbn(modificato.getIsbn());
        originale.setGenere(modificato.getGenere());
        originale.setValutazione(modificato.getValutazione());
        originale.setStatoLettura(modificato.getStatoLettura());
    }
}
