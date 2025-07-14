package progetto.memento;

import java.util.LinkedList;

public class Caretaker {

    private final int MAX = 10;
    private final LinkedList<Memento> undo;

    public Caretaker() {
        undo = new LinkedList<>();
    }

    public void addMemento(Memento m) {
        if (undo.size() >= MAX) {
            undo.removeFirst();
        }
        undo.addLast(m);
    }

    public Memento undo() {
        if(!undo.isEmpty()) {
            return undo.removeLast();
        }
        return null;
    }

}
