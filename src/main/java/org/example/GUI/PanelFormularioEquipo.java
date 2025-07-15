package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel de formulario para inscribir equipos en un torneo.
 * Permite ingresar nombre del equipo, integrantes y correo de contacto.
 */
public class PanelFormularioEquipo extends JPanel {
    private JTextField fieldNombreEquipo;
    private JTextField fieldCorreo;
    private JTextField fieldNuevoIntegrante;
    private DefaultListModel<String> integrantesModel;
    private JList<String> integrantesList;
    private PanelButton agregarIntegranteBtn;
    private PanelButton eliminarIntegranteBtn;
    private int maxIntegrantes;

    public PanelFormularioEquipo(int maxIntegrantes) {
        this.maxIntegrantes = 11;
        setOpaque(false);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 16);
        int preferredHeight = fieldFont.getSize() + 10;
        Dimension preferredFieldSize = new Dimension(200, preferredHeight);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);

        // Campo: Nombre del equipo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelNombre = new JLabel("Nombre del Equipo:");
        labelNombre.setFont(labelFont);
        add(labelNombre, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        fieldNombreEquipo = new JTextField(15);
        fieldNombreEquipo.setFont(fieldFont);
        fieldNombreEquipo.setPreferredSize(preferredFieldSize);
        add(fieldNombreEquipo, gbc);

        // Sección de Integrantes
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 10, 5);
        JLabel labelIntegrantes = new JLabel("Integrantes del Equipo:");
        labelIntegrantes.setFont(new Font("SansSerif", Font.BOLD, 18));
        labelIntegrantes.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelIntegrantes, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 5, 10, 5);

        // Campo para agregar nuevo integrante
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelNuevoIntegrante = new JLabel("Agregar Integrante:");
        labelNuevoIntegrante.setFont(labelFont);
        add(labelNuevoIntegrante, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        fieldNuevoIntegrante = new JTextField(15);
        fieldNuevoIntegrante.setFont(fieldFont);
        fieldNuevoIntegrante.setPreferredSize(preferredFieldSize);
        add(fieldNuevoIntegrante, gbc);

        // Botones para agregar/eliminar integrantes
        gbc.gridx = 1;
        gbc.gridy = 3;
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botonesPanel.setOpaque(false);

        agregarIntegranteBtn = new PanelButton("Agregar", labelFont);
        agregarIntegranteBtn.setButtonPreferredSize(new Dimension(100, 30));
        botonesPanel.add(agregarIntegranteBtn);

        eliminarIntegranteBtn = new PanelButton("Eliminar", labelFont);
        eliminarIntegranteBtn.setButtonPreferredSize(new Dimension(100, 30));
        botonesPanel.add(eliminarIntegranteBtn);

        add(botonesPanel, gbc);

        // Lista de integrantes
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        integrantesModel = new DefaultListModel<>();
        integrantesList = new JList<>(integrantesModel);
        integrantesList.setFont(fieldFont);
        integrantesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(integrantesList);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        add(scrollPane, gbc);

        // Contador de integrantes
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel contadorLabel = new JLabel("Máximo 11 integrantes");
        contadorLabel.setFont(labelFont);
        add(contadorLabel, gbc);

        // Sección de Datos de Contacto
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 15, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel labelDatosContacto = new JLabel("Datos de Contacto:");
        labelDatosContacto.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(labelDatosContacto, gbc);

        // Campo: Correo
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 5);

        JLabel labelCorreo = new JLabel("Correo:");
        labelCorreo.setFont(labelFont);
        add(labelCorreo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        fieldCorreo = new JTextField(15);
        fieldCorreo.setFont(fieldFont);
        fieldCorreo.setPreferredSize(new Dimension(200, 30));
        add(fieldCorreo, gbc);

        // Listeners
        agregarIntegranteBtn.addActionListener(e -> agregarIntegrante());
        eliminarIntegranteBtn.addActionListener(e -> eliminarIntegrante());
    }

    private void agregarIntegrante() {
        String nombre = fieldNuevoIntegrante.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un nombre", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (integrantesModel.size() >= maxIntegrantes) {
            JOptionPane.showMessageDialog(this,
                    "El equipo ya tiene el máximo de " + maxIntegrantes + " integrantes",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (integrantesModel.contains(nombre)) {
            JOptionPane.showMessageDialog(this,
                    "Este integrante ya está en el equipo",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        integrantesModel.addElement(nombre);
        fieldNuevoIntegrante.setText("");
    }

    private void eliminarIntegrante() {
        int selectedIndex = integrantesList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un integrante para eliminar",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        integrantesModel.remove(selectedIndex);
    }

    public String getNombreEquipo() {
        return fieldNombreEquipo.getText().trim();
    }

    public String getCorreo() {
        String correo = fieldCorreo.getText().trim();
        if (!correo.endsWith("@gmail.com")) {
            correo += "@gmail.com";
        }
        return correo;
    }

    public List<String> getIntegrantes() {
        List<String> integrantes = new ArrayList<>();
        for (int i = 0; i < integrantesModel.size(); i++) {
            integrantes.add(integrantesModel.getElementAt(i));
        }
        return integrantes;
    }

    public void clearFields() {
        fieldNombreEquipo.setText("");
        fieldCorreo.setText("");
        fieldNuevoIntegrante.setText("");
        integrantesModel.clear();
    }
}
