package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class PanelPrincipal extends JPanel {
    private JPanel panelActual;
    private final EnumMap<PanelType, JPanel> panelContenedor;

    public PanelPrincipal() {
        super(new BorderLayout());
        panelContenedor = new EnumMap<>(PanelType.class);

        for (PanelType panelType : PanelType.values()) {
            panelContenedor.put(panelType, panelType.crearPanel());
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends JPanel & PanelConfigurable> T getPanel(PanelType panelType) {
        JPanel panel = panelContenedor.get(panelType);

        if (panel == null) {
            throw new IllegalArgumentException("Panel con ID (" + panelType.getID() + ") no es del tipo esperado o no existe.");
        }
        return (T) panel;
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