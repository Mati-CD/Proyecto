package org.example.GUI;

import org.example.CodigoLogico.*;
import javax.swing.*;
import java.awt.*;

/**
 * Panel que permite al organizador iniciar un torneo.
 * Incluye un botón para generar manualmente el bracket del torneo.
 */
public class PanelIniciarTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private PanelButton crearBracketBtn;
    private boolean listenersActivos = false;

    /**
     * Constructor que configura el panel gráfico.
     * Se establece el layout, los botones y el título.
     */
    public PanelIniciarTorneo() {
        super(new BorderLayout());
        setBackground(new Color(88, 150, 234));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Panel superior con botón de volver
        irAtrasBtn = new PanelButton("Volver atrás", font);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(irAtrasBtn, BorderLayout.WEST);

        // Título centrado
        JLabel titleLabel = new JLabel("Iniciar Torneo", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Botón de generar bracket
        crearBracketBtn = new PanelButton("Generar Bracket manualmente", font);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(crearBracketBtn, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.CENTER);
    }

    /**
     * Inicializa el panel asignando acciones a los botones.
     *
     * @param actionAssigner Asignador de acciones para los botones.
     * @param gestorTorneos  Gestor que contiene la lógica de torneos.
     */
    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;

        if (!listenersActivos) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            listenersActivos = true;
        }

        this.revalidate();
        this.repaint();
    }

    /**
     * Método invocado cuando hay una actualización desde el modelo observado.
     * (No implementado por ahora).
     *
     * @param mensaje Mensaje de actualización.
     */
    @Override
    public void actualizar(String mensaje) {
        // No implementado
    }
}