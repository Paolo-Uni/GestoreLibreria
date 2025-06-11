package progetto.repository;

//In futuro si potranno aggiungere a questa classe altri formati per il salvataggio
public class RepositoryFactory {

    public static Repository creaRepository(String tipo) {
        if ("json".equalsIgnoreCase(tipo)) {
            return new JsonRepository();
        }
        throw new IllegalArgumentException("Tipo di repository non supportato: " + tipo);
    }
}
