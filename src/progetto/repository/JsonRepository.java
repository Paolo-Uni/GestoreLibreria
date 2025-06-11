package progetto.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import progetto.Libro;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonRepository implements Repository {
    private static final String FILE_PATH = System.getProperty("user.home") + "/Desktop/libri.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void salva(List<Libro> libri) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), libri);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei libri: " + e.getMessage());
        }
    }

    @Override
    public List<Libro> carica() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return new ArrayList<>();
            return mapper.readValue(file, new TypeReference<List<Libro>>() {});
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento dei libri: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void salva(Libro libro) {
        List<Libro> lista = carica();
        for(Libro l : lista)
            if (l.getIsbn().equals(libro.getIsbn())){
                lista.remove(l);
                break;
            }
        lista.add(libro);
        salva(lista);
    }
}
