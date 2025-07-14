package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelFormularioTorneo extends JPanel {
    private JTextField fieldNombre;
    private JTextField fieldDisciplina;
    private JComboBox<String> comboTipoInscripcion;
    private JComboBox<Integer> comboNumParticipantes;

    public PanelFormularioTorneo() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 50));

        Font font1 = new Font("SansSerif", Font.BOLD, 20);

        int preferredHeight = font1.getSize() + 10;
        Dimension preferredFieldSize = new Dimension(200, preferredHeight);
        Dimension preferredComboSize = new Dimension(220, preferredHeight);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 100, 0);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.insets = new Insets(0, 10, 100, 0);
        gbcField.anchor = GridBagConstraints.WEST;
        gbcField.weightx = 1.0;
        gbcField.fill = GridBagConstraints.HORIZONTAL;

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
        gbc.gridy = 2;
        JLabel labelTipoInscripcion = new JLabel("Tipo de Inscripción:");
        labelTipoInscripcion.setFont(font1);
        add(labelTipoInscripcion, gbc);

        gbcField.gridy = 2;
        String[] tiposDeInscripcion = {"Individual", "Equipo"};
        comboTipoInscripcion = new JComboBox<>(tiposDeInscripcion);
        comboTipoInscripcion.setFont(font1);
        comboTipoInscripcion.setPreferredSize(preferredComboSize);
        add(comboTipoInscripcion, gbcField);

        // Número de Participantes
        gbc.gridy = 3;
        JLabel labelNumParticipantes = new JLabel("Número de Participantes:"); //
        labelNumParticipantes.setFont(font1);
        add(labelNumParticipantes, gbc);

        gbcField.gridy = 3;
        Integer[] numParticipantesOpciones = {2, 4, 8, 16, 32};
        comboNumParticipantes = new JComboBox<>(numParticipantesOpciones);
        comboNumParticipantes.setFont(font1);
        comboNumParticipantes.setPreferredSize(preferredComboSize);
        comboNumParticipantes.setSelectedIndex(0);
        add(comboNumParticipantes, gbcField);
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

    public int getNumParticipantes() {
        return (Integer) comboNumParticipantes.getSelectedItem();
    }

    public void clearFields() {
        fieldNombre.setText("");
        fieldDisciplina.setText("");
        comboTipoInscripcion.setSelectedIndex(0);
        comboNumParticipantes.setSelectedIndex(0);
    }
}
