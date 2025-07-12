package org.example.GUI;

import org.example.CodigoLogico.Torneo;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelCrearTorneo extends JPanel implements PanelConfigurable {
    private PanelButton irAtrasBtn;
    private PanelButton crearTorneoBtn;
    private JTextField nombreTorneoField;
    private static List<Torneo> torneosCreados = new ArrayList<>();
    private boolean listenersAdded = false;

    public PanelCrearTorneo() {
        super(new BorderLayout());
        setBackground(new Color(255, 255, 200));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("SansSerif", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Panel superior con botón de volver
        irAtrasBtn = new PanelButton("Volver atrás", font);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(irAtrasBtn, BorderLayout.WEST);

        // Título centrado
        JLabel titleLabel = new JLabel("Crear Nuevo Torneo", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Panel central con formulario simplificado
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // Solo campo para el nombre del torneo
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre del Torneo:"), gbc);

        gbc.gridx = 1;
        nombreTorneoField = new JTextField(20);
        nombreTorneoField.setFont(font);
        formPanel.add(nombreTorneoField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Panel inferior con botón de crear torneo
        crearTorneoBtn = new PanelButton("Crear Torneo", font);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(crearTorneoBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void inicializar(ActionAssigner actionAssigner) {
        if (!listenersAdded) {
            irAtrasBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
            crearTorneoBtn.addActionListener(e -> crearTorneo());
            listenersAdded = true;
        }
        this.revalidate();
        this.repaint();
    }

    private void crearTorneo() {
        String nombre = nombreTorneoField.getText().trim();

        if (nombre.isEmpty()) {
            showMessageOnce("Por favor ingrese un nombre para el torneo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si ya existe un torneo con ese nombre
        for (Torneo t : torneosCreados) {
            if (t.getNombre().equals(nombre)) {
                showMessageOnce("Ya existe un torneo con ese nombre", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Crear torneo solo con el nombre
        Torneo nuevoTorneo = new Torneo(nombre);
        torneosCreados.add(nuevoTorneo);

        showMessageOnce("Torneo creado exitosamente:\nNombre: " + nombre,
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        nombreTorneoField.setText("");
    }

    private void showMessageOnce(String message, String title, int messageType) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, message, title, messageType);
        });
    }

    public static List<Torneo> getTorneosCreados() {
        return torneosCreados;
    }
}