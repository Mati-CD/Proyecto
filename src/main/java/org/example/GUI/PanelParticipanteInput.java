package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelParticipanteInput extends JPanel {
    private JTextField fieldNombreParticipante;
    private JTextField fieldEdadParticipante;
    private JTextField fieldPaisParticipante;

    public PanelParticipanteInput() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        Font font = new Font("SansSerif", Font.BOLD, 18);

        int preferredHeight = font.getSize() + 10;
        Dimension preferredFieldSize = new Dimension(200, preferredHeight);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.gridx = 0;

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.insets = new Insets(10, 10, 10, 0);
        gbcField.anchor = GridBagConstraints.WEST;
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.weightx = 1.0;
        gbcField.gridx = 1;

        // Nombre del Participante
        gbc.gridy = 0;
        JLabel labelNombreParticipante = new JLabel("Nombre del Participante:");
        labelNombreParticipante.setFont(font);
        add(labelNombreParticipante, gbc);

        gbcField.gridy = 0;
        fieldNombreParticipante = new JTextField(20);
        fieldNombreParticipante.setFont(font);
        fieldNombreParticipante.setPreferredSize(preferredFieldSize);
        add(fieldNombreParticipante, gbcField);

        // Edad del Participante
        gbc.gridy = 1;
        JLabel labelEdadParticipante = new JLabel("Edad:");
        labelEdadParticipante.setFont(font);
        add(labelEdadParticipante, gbc);

        gbcField.gridy = 1;
        fieldEdadParticipante = new JTextField(20);
        fieldEdadParticipante.setFont(font);
        fieldEdadParticipante.setPreferredSize(preferredFieldSize);
        add(fieldEdadParticipante, gbcField);

        // País del Participante
        gbc.gridy = 2;
        JLabel labelPaisParticipante = new JLabel("País:");
        labelPaisParticipante.setFont(font);
        add(labelPaisParticipante, gbc);

        gbcField.gridy = 2;
        fieldPaisParticipante = new JTextField(20);
        fieldPaisParticipante.setFont(font);
        fieldPaisParticipante.setPreferredSize(preferredFieldSize);
        add(fieldPaisParticipante, gbcField);
    }

    public String getNombreParticipante() {
        return fieldNombreParticipante.getText();
    }

    public String getEdadParticipante() {
        return fieldEdadParticipante.getText();
    }

    public String getPaisParticipante() {
        return fieldPaisParticipante.getText();
    }

    public void clearFields() {
        fieldNombreParticipante.setText("");
        fieldEdadParticipante.setText("");
        fieldPaisParticipante.setText("");
    }
}
