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
            return mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento dei libri: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
