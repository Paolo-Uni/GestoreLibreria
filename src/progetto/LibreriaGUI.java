package progetto;

import progetto.command.Command;
import progetto.command.ModificaCommand;
import progetto.filtri.Filtro;
import progetto.filtri.FiltroPerGenere;
import progetto.filtri.FiltroPerStato;
import progetto.filtri.FiltroPerValutazioneMinima;
import progetto.ordina.Ordina;
import progetto.ordina.OrdinaPerAutore;
import progetto.ordina.OrdinaPerTitolo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LibreriaGUI extends JFrame {
    private final GestoreLibreria gestore;
    private final JTable tabellaLibri;
    private final DefaultTableModel modelloTabella;
    private final JComboBox<String> filtroGenere;
    private final JComboBox<String> filtroStato;
    private final JComboBox<String> ordinaPer;
    private final JTextField campoRicercaTitolo;
    private final JTextField campoRicercaAutore;
    private final JTextField campoValutazioneMinima;
    private final Map<String, Ordina> strategieOrdine;

    public LibreriaGUI() {
        gestore = GestoreLibreria.getInstance();
        strategieOrdine = Map.of(
                "Titolo", new OrdinaPerTitolo(),
                "Autore", new OrdinaPerAutore()
        );

        setTitle("Gestore Libreria Personale");
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] colonne = {"Titolo", "Autore", "ISBN", "Genere", "Valutazione", "Stato"};
        modelloTabella = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabellaLibri = new JTable(modelloTabella);
        tabellaLibri.getTableHeader().setReorderingAllowed(false);
        tabellaLibri.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabellaLibri), BorderLayout.CENTER);

        JPanel pannelloBottoni = new JPanel();
        JButton btnAggiungi = new JButton("Aggiungi");
        JButton btnRimuovi = new JButton("Rimuovi");
        JButton btnModifica = new JButton("Modifica");
        JButton btnHome = new JButton("Home");
        pannelloBottoni.add(btnAggiungi);
        pannelloBottoni.add(btnRimuovi);
        pannelloBottoni.add(btnModifica);
        pannelloBottoni.add(btnHome);
        add(pannelloBottoni, BorderLayout.SOUTH);

        JPanel pannelloRicerca = new JPanel();
        campoRicercaTitolo = new JTextField(10);
        campoRicercaAutore = new JTextField(10);
        campoValutazioneMinima = new JTextField(3);
        JButton btnCerca = new JButton("Cerca");
        pannelloRicerca.add(new JLabel("Titolo:")); pannelloRicerca.add(campoRicercaTitolo);
        pannelloRicerca.add(new JLabel("Autore:")); pannelloRicerca.add(campoRicercaAutore);
        pannelloRicerca.add(new JLabel("Valutazione >=:")); pannelloRicerca.add(campoValutazioneMinima);
        pannelloRicerca.add(btnCerca);
        add(pannelloRicerca, BorderLayout.NORTH);

        JPanel pannelloFiltri = new JPanel();
        pannelloFiltri.setLayout(new GridLayout(0, 1));
        filtroGenere = new JComboBox<>(new String[]{"Tutti", "Narrativa", "Saggio", "Fantasy", "Storico"});
        filtroStato = new JComboBox<>(new String[]{"Tutti", "LETTO", "DA_LEGGERE", "IN_LETTURA"});
        ordinaPer = new JComboBox<>(new String[]{"Titolo", "Autore"});
        Dimension comboSize = new Dimension(120, 25);
        filtroGenere.setPreferredSize(comboSize);
        filtroStato.setPreferredSize(comboSize);
        ordinaPer.setPreferredSize(comboSize);

        pannelloFiltri.add(new JLabel("Genere:")); pannelloFiltri.add(filtroGenere);
        pannelloFiltri.add(new JLabel("Stato:")); pannelloFiltri.add(filtroStato);
        pannelloFiltri.add(new JLabel("Ordina per:")); pannelloFiltri.add(ordinaPer);
        add(pannelloFiltri, BorderLayout.WEST);

        btnAggiungi.addActionListener(e -> {
            Libro nuovo = inputLibro(null);
            if (nuovo != null) {
                gestore.aggiungiLibro(nuovo);
                aggiornaTabella();
            }
        });
        btnRimuovi.addActionListener(e -> {
            int riga = tabellaLibri.getSelectedRow();
            if (riga != -1) {
                String isbn = (String) modelloTabella.getValueAt(riga, 2);
                gestore.rimuoviLibro(isbn);
                aggiornaTabella();
            }
        });
        btnModifica.addActionListener(e -> {
            int riga = tabellaLibri.getSelectedRow();
            if (riga != -1) {
                String isbn = (String) modelloTabella.getValueAt(riga, 2);
                Libro vecchio = gestore.getTuttiLibri().stream()
                        .filter(l -> l.getIsbn().equals(isbn)).findFirst().orElse(null);
                if (vecchio != null) {
                    Libro modificato = inputLibro(vecchio);
                    if (modificato != null) {
                        Command comando = new ModificaCommand(vecchio, modificato);
                        comando.esegui();
                        gestore.salvaLibri();
                        aggiornaTabella();
                    }
                }
            }
        });

        filtroGenere.addActionListener(e -> applicaFiltri());
        filtroStato.addActionListener(e -> applicaFiltri());
        ordinaPer.addActionListener(e -> aggiornaTabella());
        btnCerca.addActionListener(e -> applicaRicerca());
        btnHome.addActionListener(e -> {
            filtroGenere.setSelectedIndex(0);
            filtroStato.setSelectedIndex(0);
            ordinaPer.setSelectedIndex(0);
            campoRicercaTitolo.setText("");
            campoRicercaAutore.setText("");
            campoValutazioneMinima.setText("");
            aggiornaTabella();
        });

        aggiornaTabella();
        setVisible(true);
    }

    private void applicaFiltri() {
        List<Filtro> filtri = new ArrayList<>();
        String genere = (String) filtroGenere.getSelectedItem();
        if (!"Tutti".equals(genere)) filtri.add(new FiltroPerGenere(genere));

        String stato = (String) filtroStato.getSelectedItem();
        if (!"Tutti".equals(stato)) filtri.add(new FiltroPerStato(Libro.StatoLettura.valueOf(stato)));

        List<Libro> risultati = gestore.getTuttiLibri();
        for (Filtro filtro : filtri) {
            risultati = gestore.filtraLibri(risultati, filtro);
        }

        campoRicercaTitolo.setText("");
        campoRicercaAutore.setText("");
        campoValutazioneMinima.setText("");

        mostraInTabella(risultati);
    }

    private void aggiornaTabella() {

        Ordina strategia = strategieOrdine.get((String) ordinaPer.getSelectedItem());
        List<Libro> risultati = gestore.getTuttiLibri();
        if (strategia != null) risultati = gestore.getLibriOrdinati(strategia);

        filtroGenere.setSelectedIndex(0);
        filtroStato.setSelectedIndex(0);

        mostraInTabella(risultati);
    }

    private void applicaRicerca() {
        String titolo = campoRicercaTitolo.getText().trim();
        String autore = campoRicercaAutore.getText().trim();
        boolean hasTitolo = !titolo.isEmpty();
        boolean hasAutore = !autore.isEmpty();

        List<Libro> risultati;
        if (hasTitolo && hasAutore) {
            List<Libro> perTitolo = gestore.cercaPerTitolo(titolo);
            List<Libro> perAutore = gestore.cercaPerAutore(autore);
            perTitolo.retainAll(perAutore);
            risultati = perTitolo;
        } else if (hasTitolo) {
            risultati = gestore.cercaPerTitolo(titolo);
        } else if (hasAutore) {
            risultati = gestore.cercaPerAutore(autore);
        } else {
            risultati = gestore.getTuttiLibri();
        }

        try {
            int minVal = Integer.parseInt(campoValutazioneMinima.getText().trim());
            List<Libro> tmp = new ArrayList<>();
            if (minVal >= 1 && minVal <= 5) {
                Filtro filtroVal = new FiltroPerValutazioneMinima(minVal);
                tmp = gestore.filtraLibri(filtroVal);
            }
            if(!tmp.isEmpty())
                risultati.retainAll(tmp);
        } catch (Exception ignored) {}

        Ordina strategia = strategieOrdine.get((String) ordinaPer.getSelectedItem());
        if (strategia != null) risultati = strategia.ordina(risultati);

        filtroGenere.setSelectedIndex(0);
        filtroStato.setSelectedIndex(0);

        mostraInTabella(risultati);
    }

    private void mostraInTabella(List<Libro> libri) {
        modelloTabella.setRowCount(0);
        for (Libro l : libri) {
            modelloTabella.addRow(new Object[]{
                    l.getTitolo(),
                    l.getAutore(),
                    l.getIsbn(),
                    l.getGenere(),
                    l.getValutazione(),
                    l.getStatoLettura()
            });
        }
    }

    private Libro inputLibro(Libro esistente) {
        JTextField titolo = new JTextField(esistente != null ? esistente.getTitolo() : "");
        JTextField autore = new JTextField(esistente != null ? esistente.getAutore() : "");
        JTextField isbn = new JTextField(esistente != null ? esistente.getIsbn() : "");
        JTextField genere = new JTextField(esistente != null ? esistente.getGenere() : "");
        JTextField valutazione = new JTextField(esistente != null ? String.valueOf(esistente.getValutazione()) : "");
        JComboBox<Libro.StatoLettura> stato = new JComboBox<>(Libro.StatoLettura.values());
        if (esistente != null) stato.setSelectedItem(esistente.getStatoLettura());

        JPanel pannello = new JPanel(new GridLayout(0, 2));
        pannello.add(new JLabel("Titolo:")); pannello.add(titolo);
        pannello.add(new JLabel("Autore:")); pannello.add(autore);
        pannello.add(new JLabel("ISBN:")); pannello.add(isbn);
        pannello.add(new JLabel("Genere:")); pannello.add(genere);
        pannello.add(new JLabel("Valutazione (1-5):")); pannello.add(valutazione);
        pannello.add(new JLabel("Stato lettura:")); pannello.add(stato);

        int result = JOptionPane.showConfirmDialog(this, pannello, "Dettagli libro",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                return new Libro(
                        titolo.getText(),
                        autore.getText(),
                        isbn.getText(),
                        genere.getText(),
                        Integer.parseInt(valutazione.getText()),
                        (Libro.StatoLettura) stato.getSelectedItem()
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Errore nei dati inseriti.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibreriaGUI::new);
    }
}
