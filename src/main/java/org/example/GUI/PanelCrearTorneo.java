package org.example.GUI;

import org.example.CodigoLogico.CrearTorneo;
import org.example.CodigoLogico.GestorTorneos;
import org.example.CodigoLogico.Torneo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelCrearTorneo extends JPanel implements PanelConfigurable {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private PanelButton crearTorneoBtn;
    private PanelEntradaTorneo panelEntradaTorneo;
    private boolean listenersAdded = false;

    public PanelCrearTorneo() {
        super(new BorderLayout());
        setBackground(new Color(255, 255, 200));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 12);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        irAtrasBtn = new PanelButton("Volver atrás", font);
        irAtrasBtn.setButtonPreferredSize(new Dimension(120, 30));
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAtrasBtn);
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Crear Nuevo Torneo", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        panelEntradaTorneo = new PanelEntradaTorneo();
        JPanel centerLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        centerLeftPanel.setOpaque(false);
        centerLeftPanel.add(panelEntradaTorneo);
        centerPanel.add(centerLeftPanel, BorderLayout.WEST);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);

        crearTorneoBtn = new PanelButton("Crear Torneo", font);
        crearTorneoBtn.setButtonPreferredSize(new Dimension(200, 50));
        bottomPanel.add(crearTorneoBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner, GestorTorneos gestorTorneos) {
        this.gestorTorneos = gestorTorneos;
        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            crearTorneoBtn.addActionListener(e -> clickCrearBtn(e, actionAssigner));
            listenersAdded = true;
        }
        this.revalidate();
        this.repaint();
    }

    private void clickCrearBtn(ActionEvent e, ActionAssigner actionAssigner) {
        String nombre = panelEntradaTorneo.getNombre().trim();
        String disciplina = panelEntradaTorneo.getDisciplina().trim();
        String tipoDeInscripcion = panelEntradaTorneo.getTipoInscripcion();

        if (nombre.isEmpty() || disciplina.isEmpty()) {
            showMessageOnce("Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (gestorTorneos.torneoExiste(nombre)) {
            showMessageOnce("Ya existe un torneo con este nombre", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear torneo con nombre, disciplina y tipo de Inscripcion
        CrearTorneo nuevoTorneo = new CrearTorneo(nombre, disciplina, tipoDeInscripcion);
        Torneo torneo = new Torneo(nuevoTorneo);
        gestorTorneos.add(torneo);

        showMessageOnce("Torneo creado exitosamente:\nNombre: " + nombre +
                        "\nDisciplina: " + disciplina +
                        "\nTipo de Inscripción: " + tipoDeInscripcion,
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Limpiar campos después de crear
        panelEntradaTorneo.clearFields();
        actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()).actionPerformed(e);
    }

    private void showMessageOnce(String message, String title, int messageType) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, message, title, messageType);
        });
    }
}