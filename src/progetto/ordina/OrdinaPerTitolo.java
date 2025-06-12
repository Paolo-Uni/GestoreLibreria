package progetto.ordina;

import progetto.Libro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrdinaPerTitolo implements Ordina {
    @Override
    public List<Libro> ordina(List<Libro> libri) {
        Map<String, List<Libro>> mappa = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (Libro libro : libri) {
            String chiave = libro.getTitolo();
            List<Libro> gruppo = mappa.get(chiave);
            if (gruppo == null) {
                gruppo = new ArrayList<>();
                mappa.put(chiave, gruppo);
            }
            gruppo.add(libro);
        }

        List<Libro> ordinati = new ArrayList<>();
        for (List<Libro> gruppo : mappa.values()) {
            ordinati.addAll(gruppo);
        }
        return ordinati;
    }
}
