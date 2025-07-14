package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelCrearTorneo extends JPanel implements PanelConfigurable, TorneoObserver {
    private GestorTorneos gestorTorneos;
    private PanelButton irAtrasBtn;
    private PanelButton crearTorneoBtn;
    private PanelFormularioTorneo panelFormularioTorneo;
    private boolean listenersActivos = false;

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

        panelFormularioTorneo = new PanelFormularioTorneo();
        JPanel centerLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        centerLeftPanel.setOpaque(false);
        centerLeftPanel.add(panelFormularioTorneo);
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
        this.gestorTorneos.registrarObserver(this);

        if (!listenersActivos) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            crearTorneoBtn.addActionListener(e -> clickCrearBtn(e, actionAssigner));
            listenersActivos = true;
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actualizar(String mensaje) {
        if (mensaje.contains("ERROR") || mensaje.contains("Ya existe un torneo")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mensaje.contains("Torneo creado exitosamente:")) {
            GuiUtilidades.showMessageOnce(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clickCrearBtn(ActionEvent e, ActionAssigner actionAssigner) {
        String nombre = panelFormularioTorneo.getNombre().trim();
        String disciplina = panelFormularioTorneo.getDisciplina().trim();
        String tipoDeInscripcion = panelFormularioTorneo.getTipoInscripcion();

        if (nombre.isEmpty() || disciplina.isEmpty()) {
            GuiUtilidades.showMessageOnce(this, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear torneo con nombre, disciplina y tipo de Inscripcion
        Torneo torneo = new Torneo(nombre, disciplina, tipoDeInscripcion);
        gestorTorneos.addTorneo(torneo);

        if (gestorTorneos.getCreadoConExito()) {
            panelFormularioTorneo.clearFields();
            actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()).actionPerformed(e);
        }
    }
}