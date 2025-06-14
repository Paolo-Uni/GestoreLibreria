package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import progetto.LibreriaGUI;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class LibreriaGUITest {

    private LibreriaGUI gui;

    @BeforeEach
    void setUp() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> gui = new LibreriaGUI());
    }

    @Test
    void testGUIInizializzataConSuccesso() {
        assertNotNull(gui);
        assertTrue(gui.isVisible());
        assertEquals("Gestore Libreria Personale", gui.getTitle());
    }

    @Test
    void testComponentiPresenti() {
        JTable tabella = null;
        for (java.awt.Component c : gui.getContentPane().getComponents()) {
            if (c instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) c;
                if (scrollPane.getViewport().getView() instanceof JTable) {
                    tabella = (JTable) scrollPane.getViewport().getView();
                    break;
                }
            }
        }
        assertNotNull(tabella);
        assertEquals(6, tabella.getColumnCount());
    }
}
