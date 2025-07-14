package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

/**
 * Panel principal que actúa como contenedor para los distintos paneles de la aplicación.
 * Se encarga de inicializar todos los paneles y permitir el cambio dinámico entre ellos.
 */
public class PanelPrincipal extends JPanel {
    private JPanel panelActual;
    private final EnumMap<PanelType, JPanel> panelContenedor;

    /**
     * Constructor que inicializa todos los paneles definidos en el enum PanelType
     * y los guarda en un mapa para acceso rápido.
     */
    public PanelPrincipal() {
        super(new BorderLayout());
        panelContenedor = new EnumMap<>(PanelType.class);

        for (PanelType panelType : PanelType.values()) {
            panelContenedor.put(panelType, panelType.crearPanel());
        }
    }

    /**
     * Retorna un panel del tipo solicitado desde el mapa de paneles.
     *
     * @param panelType tipo de panel a obtener, según el enum PanelType.
     * @return el panel correspondiente, ya creado, que implementa PanelConfigurable.
     * @param <T> tipo genérico que extiende JPanel e implementa PanelConfigurable.
     * @throws IllegalArgumentException si el panel no existe o no es del tipo esperado.
     */
    @SuppressWarnings("unchecked")
    public <T extends JPanel & PanelConfigurable> T getPanel(PanelType panelType) {
        JPanel panel = panelContenedor.get(panelType);

        if (panel == null) {
            throw new IllegalArgumentException("Panel con ID (" + panelType.getID() + ") no es del tipo esperado o no existe.");
        }
        return (T) panel;
    }

    /**
     * Cambia el panel actualmente visible por uno nuevo.
     *
     * @param nuevoPanel el nuevo panel que se debe mostrar en la interfaz.
     */
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
