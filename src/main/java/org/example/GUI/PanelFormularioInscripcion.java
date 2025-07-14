package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelFormularioInscripcion extends JPanel {
    private JTextField fieldNombre;
    private JTextField fieldPais;
    private JTextField fieldEdad;
    private JTextField fieldCorreoUsuario;

    public PanelFormularioInscripcion() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 16);
        int preferredHeight = fieldFont.getSize() + 10;
        Dimension preferredFieldSize = new Dimension(200, preferredHeight);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setFont(labelFont);
        add(labelNombre, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        fieldNombre = new JTextField(15);
        fieldNombre.setFont(fieldFont);
        fieldNombre.setPreferredSize(preferredFieldSize);
        add(fieldNombre, gbc);

        // Datos de Contacto
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 5, 15, 5);
        JLabel labelDatosContacto = new JLabel("Datos de Contacto:");
        labelDatosContacto.setFont(new Font("SansSerif", Font.BOLD, 18));
        labelDatosContacto.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelDatosContacto, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 5, 10, 5);

        // País
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelPais = new JLabel("País:");
        labelPais.setFont(labelFont);
        add(labelPais, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        fieldPais = new JTextField(15);
        fieldPais.setFont(fieldFont);
        fieldPais.setPreferredSize(preferredFieldSize);
        add(fieldPais, gbc);

        // Edad
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelEdad = new JLabel("Edad:");
        labelEdad.setFont(labelFont);
        add(labelEdad, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        fieldEdad = new JTextField(15);
        fieldEdad.setFont(fieldFont);
        fieldEdad.setPreferredSize(preferredFieldSize);
        add(fieldEdad, gbc);

        // Correo
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelCorreo = new JLabel("Correo:");
        labelCorreo.setFont(labelFont);
        add(labelCorreo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo de correo y "@gmail.com"
        JPanel correoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        correoPanel.setOpaque(false);
        fieldCorreoUsuario = new JTextField(10);
        fieldCorreoUsuario.setFont(fieldFont);
        fieldCorreoUsuario.setPreferredSize(new Dimension(preferredFieldSize.width - 80, preferredHeight));
        correoPanel.add(fieldCorreoUsuario);
        JLabel gmailLabel = new JLabel("@gmail.com");
        gmailLabel.setFont(fieldFont);
        correoPanel.add(gmailLabel);
        add(correoPanel, gbc);
    }

    public String getNombre() {
        return fieldNombre.getText().trim();
    }

    public String getPais() {
        return fieldPais.getText().trim();
    }

    public int getEdad() {
        try {
            return Integer.parseInt(fieldEdad.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getCorreo() {
        return fieldCorreoUsuario.getText().trim() + "@gmail.com";
    }

    public void clearFields() {
        fieldNombre.setText("");
        fieldPais.setText("");
        fieldEdad.setText("");
        fieldCorreoUsuario.setText("");
    }
}
