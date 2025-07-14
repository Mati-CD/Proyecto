package org.example.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de formulario para ingresar datos de inscripción individual.
 */
public class PanelFormularioInscripcion extends JPanel {
    private JTextField fieldNombre;
    private JTextField fieldPais;
    private JTextField fieldEdad;
    private JTextField fieldCorreoUsuario;

    /**
     * Crea e inicializa el formulario con los campos necesarios para inscribir un participante.
     */
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setFont(labelFont);
        add(labelNombre, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        fieldNombre = new JTextField(15);
        fieldNombre.setFont(fieldFont);
        fieldNombre.setPreferredSize(preferredFieldSize);
        add(fieldNombre, gbc);

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

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelPais = new JLabel("País:");
        labelPais.setFont(labelFont);
        add(labelPais, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        fieldPais = new JTextField(15);
        fieldPais.setFont(fieldFont);
        fieldPais.setPreferredSize(preferredFieldSize);
        add(fieldPais, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelEdad = new JLabel("Edad:");
        labelEdad.setFont(labelFont);
        add(labelEdad, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        fieldEdad = new JTextField(15);
        fieldEdad.setFont(fieldFont);
        fieldEdad.setPreferredSize(preferredFieldSize);
        add(fieldEdad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelCorreo = new JLabel("Correo:");
        labelCorreo.setFont(labelFont);
        add(labelCorreo, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

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

    /**
     * @return el nombre ingresado en el formulario
     */
    public String getNombre() {
        return fieldNombre.getText().trim();
    }

    /**
     * @return el país ingresado en el formulario
     */
    public String getPais() {
        return fieldPais.getText().trim();
    }

    /**
     * @return la edad ingresada como número entero, o -1 si el valor no es válido
     */
    public int getEdad() {
        try {
            return Integer.parseInt(fieldEdad.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * @return el correo electrónico ingresado en el formulario con sufijo @gmail.com
     */
    public String getCorreo() {
        return fieldCorreoUsuario.getText().trim() + "@gmail.com";
    }

    /**
     * Limpia todos los campos del formulario.
     */
    public void clearFields() {
        fieldNombre.setText("");
        fieldPais.setText("");
        fieldEdad.setText("");
        fieldCorreoUsuario.setText("");
    }
}