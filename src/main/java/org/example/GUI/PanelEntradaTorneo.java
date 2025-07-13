package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelEntradaTorneo extends JPanel {
    private JTextField fieldNombre;
    private JTextField fieldDisciplina;
    private JComboBox<String> comboTipoInscripcion;

    public PanelEntradaTorneo() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 50));

        Font font1 = new Font("SansSerif", Font.BOLD, 20);

        int preferredHeight = font1.getSize() + 10;
        Dimension preferredFieldSize = new Dimension(200, preferredHeight);
        Dimension preferredComboSize = new Dimension(220, preferredHeight);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 150, 0);

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.gridx = 1;
        gbcField.anchor = GridBagConstraints.WEST;
        gbcField.weightx = 1.0;
        gbcField.fill = GridBagConstraints.HORIZONTAL;

        gbcField.insets = new Insets(0, 10, 150, 0);

        // Nombre
        gbc.gridy = 0;
        JLabel labelNombre = new JLabel("Nombre del Torneo:");
        labelNombre.setFont(font1);
        add(labelNombre, gbc);

        gbcField.gridy = 0;
        fieldNombre = new JTextField(20);
        fieldNombre.setFont(font1);
        fieldNombre.setPreferredSize(preferredFieldSize);
        add(fieldNombre, gbcField);

        // Disciplina
        gbc.gridy = 1;
        JLabel labelDisciplina = new JLabel("Disciplina:");
        labelDisciplina.setFont(font1);
        add(labelDisciplina, gbc);

        gbcField.gridy = 1;
        fieldDisciplina = new JTextField(20);
        fieldDisciplina.setFont(font1);
        fieldDisciplina.setPreferredSize(preferredFieldSize);
        add(fieldDisciplina, gbcField);

        // Tipo de inscripcion
        gbc.gridy = 2; // Fila 2
        JLabel labelTipoInscripcion = new JLabel("Tipo de Inscripción:");
        labelTipoInscripcion.setFont(font1);
        add(labelTipoInscripcion, gbc);

        gbcField.gridy = 2;
        String[] tiposDeInscripcion = {"Individual", "Equipo"};
        comboTipoInscripcion = new JComboBox<>(tiposDeInscripcion);
        comboTipoInscripcion.setFont(font1);
        comboTipoInscripcion.setPreferredSize(preferredComboSize);
        add(comboTipoInscripcion, gbcField);
    }

    public String getNombre() {
        return fieldNombre.getText();
    }

    public String getDisciplina() {
        return fieldDisciplina.getText();
    }

    public String getTipoInscripcion() {
        return (String) comboTipoInscripcion.getSelectedItem();
    }

    public void clearFields() {
        fieldNombre.setText("");
        fieldDisciplina.setText("");
    }
}
