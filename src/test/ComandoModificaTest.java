package test;

import org.junit.jupiter.api.Test;
import progetto.Libro;
import progetto.command.ModificaCommand;
import progetto.command.Command;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComandoModificaTest {

    @Test
    void testModificaComandoAggiornaTuttiICampi() {
        // Libro originale
        Libro originale = new Libro("Titolo Vecchio", "Autore Vecchio", "111", "Storico", 3, Libro.StatoLettura.DA_LEGGERE);

        // Libro modificato con nuovi valori
        Libro modificato = new Libro("Titolo Nuovo", "Autore Nuovo", "111", "Fantasy", 5, Libro.StatoLettura.LETTO);

        // Comando e applicazione
        Command comando = new ModificaCommand(originale, modificato);
        comando.esegui();

        // Verifica dei risultati
        assertEquals("Titolo Nuovo", originale.getTitolo());
        assertEquals("Autore Nuovo", originale.getAutore());
        assertEquals("111", originale.getIsbn()); // stesso ISBN
        assertEquals("Fantasy", originale.getGenere());
        assertEquals(5, originale.getValutazione());
        assertEquals(Libro.StatoLettura.LETTO, originale.getStatoLettura());
    }
} 
