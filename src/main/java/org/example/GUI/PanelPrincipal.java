package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PanelPrincipal extends JPanel {
    private JPanel panelActual;
    private final HashMap<String, JPanel> panelContenedor;

    public PanelPrincipal() {
        super(new BorderLayout());
        panelContenedor = new HashMap<>();

        for (PanelID panel : PanelID.values()) {
            panelContenedor.put(panel.getID(), panel.crearPanel());
        }
    }

    public <T extends JPanel> T getPanel(String panelID, Class<T> panelType) {
        JPanel panel = panelContenedor.get(panelID);
        if (panelType.isInstance(panel)) {
            return panelType.cast(panel);
        }
        throw new IllegalArgumentException("Panel con ID (" + panelID + ") no es del tipo esperado o no existe.");
    }

    public void cambiarPanel(JPanel nuevoPanel) {
        if (panelActual != null) {
            remove(panelActual);
        }
        panelActual = nuevoPanel;
        add(panelActual, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
