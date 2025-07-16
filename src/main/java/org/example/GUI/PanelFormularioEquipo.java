package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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
    private JLabel contadorIntegrantesLabel;

    private int maxIntegrantes;

    private final Font labelFont = new Font("SansSerif", Font.BOLD, 16);
    private final Font fieldFont = new Font("SansSerif", Font.PLAIN, 16);
    private final int preferredHeight = fieldFont.getSize() + 10;
    private final Dimension fieldSize = new Dimension(200, preferredHeight);

    public PanelFormularioEquipo() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); // Espaciado entre componentes

        // --- Sección: Nombre del Equipo ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST; // Alinea la etiqueta a la derecha de su celda
        JLabel labelNombre = new JLabel("Nombre del Equipo:");
        labelNombre.setFont(labelFont);
        add(labelNombre, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Alinea el campo a la izquierda de su celda
        gbc.fill = GridBagConstraints.HORIZONTAL; // Permite que el campo se estire horizontalmente
        gbc.weightx = 1.0; // Le da "peso" al campo para que ocupe el espacio disponible
        fieldNombreEquipo = new JTextField(15); // Tamaño de columna inicial
        fieldNombreEquipo.setFont(fieldFont);
        fieldNombreEquipo.setPreferredSize(fieldSize);
        add(fieldNombreEquipo, gbc);

        // --- Sección: Integrantes del Equipo ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa dos columnas
        gbc.insets = new Insets(20, 5, 10, 5); // Más espacio arriba
        JLabel labelIntegrantesTitulo = new JLabel("Integrantes del Equipo:");
        labelIntegrantesTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        labelIntegrantesTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelIntegrantesTitulo, gbc);

        // Campo para agregar nuevo integrante
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Vuelve a una columna
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 5); // Vuelve al espaciado normal
        JLabel labelNuevoIntegrante = new JLabel("Agregar Integrante:");
        labelNuevoIntegrante.setFont(labelFont);
        add(labelNuevoIntegrante, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        fieldNuevoIntegrante = new JTextField(15);
        fieldNuevoIntegrante.setFont(fieldFont);
        fieldNuevoIntegrante.setPreferredSize(fieldSize);
        add(fieldNuevoIntegrante, gbc);

        // Contenedor para botones "Agregar" y "Eliminar"
        gbc.gridx = 1; // Ubicar en la segunda columna
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
        gbc.gridwidth = 2; // Ocupa ambas columnas
        gbc.fill = GridBagConstraints.BOTH; // Permite que la lista se estire en ambas direcciones
        gbc.weighty = 1.0; // Le da "peso" para que ocupe espacio vertical disponible

        integrantesModel = new DefaultListModel<>();
        integrantesList = new JList<>(integrantesModel);
        integrantesList.setFont(fieldFont);
        integrantesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(integrantesList);
        scrollPane.setPreferredSize(new Dimension(300, 150)); // Tamaño preferido inicial para el scroll
        add(scrollPane, gbc);

        // Contador de integrantes (se actualizará dinámicamente)
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; // No estirar
        gbc.weighty = 0.0; // No dar peso vertical
        gbc.anchor = GridBagConstraints.CENTER; // Centrar
        contadorIntegrantesLabel = new JLabel("Número de Integrantes: 0 (Máx: -)"); // Texto inicial
        contadorIntegrantesLabel.setFont(labelFont);
        add(contadorIntegrantesLabel, gbc);

        // --- Sección: Datos de Contacto ---
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 15, 5); // Más espacio arriba
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel labelDatosContacto = new JLabel("Datos de Contacto:");
        labelDatosContacto.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(labelDatosContacto, gbc);

        // Campo: Correo
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 5); // Vuelve al espaciado normal
        JLabel labelCorreo = new JLabel("Correo:");
        labelCorreo.setFont(labelFont);
        add(labelCorreo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Permite que el campo se estire

        // Usamos un JPanel interno para agrupar el JTextField y el "@gmail.com" estático
        JPanel correoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        correoPanel.setOpaque(false);
        fieldCorreo = new JTextField(10); // Ajusta el tamaño de columna
        fieldCorreo.setFont(fieldFont);
        fieldCorreo.setPreferredSize(new Dimension(fieldSize.width - 80, preferredHeight)); // Ajuste de tamaño
        correoPanel.add(fieldCorreo);
        JLabel gmailLabel = new JLabel("@gmail.com");
        gmailLabel.setFont(fieldFont);
        correoPanel.add(gmailLabel);
        add(correoPanel, gbc);
    }

    public void setMaxIntegrantes(int maxIntegrantes) {
        this.maxIntegrantes = maxIntegrantes;
        updateIntegrantesCountLabel();
    }

    public void updateIntegrantesCountLabel() {
        if (maxIntegrantes > 0) {
            contadorIntegrantesLabel.setText("Integrantes: " + integrantesModel.size() + " (Máx: " + maxIntegrantes + ")");
        }
        else {
            contadorIntegrantesLabel.setText("Integrantes: " + integrantesModel.size());
        }
    }

    public String getNombreEquipo() {
        return fieldNombreEquipo.getText().trim();
    }

    public String getCorreo() {
        String correo = fieldCorreo.getText().trim();
        if (!correo.isEmpty() && !correo.contains("@") && !correo.endsWith("@gmail.com")) {
            correo += "@gmail.com";
        } else if (!correo.isEmpty() && !correo.endsWith("@gmail.com") && !correo.contains("@")) {
            correo += "@gmail.com";
        }
        return correo;
    }

    public List<String> getIntegrantes() {
        List<String> integrantes = new ArrayList<>();
        for (int i=0; i < integrantesModel.size(); i++) {
            integrantes.add(integrantesModel.getElementAt(i));
        }
        return integrantes;
    }

    public String getNuevoIntegrante() {
        return fieldNuevoIntegrante.getText();
    }

    public int getSelectedIndexIntegrantesList() {
        return integrantesList.getSelectedIndex();
    }

    public DefaultListModel<String> getIntegrantesModel() {
        return integrantesModel;
    }

    public void addAgregarIntegranteListener(ActionListener actionListener) {
        agregarIntegranteBtn.addActionListener(actionListener);
    }

    public void addEliminarIntegranteListener(ActionListener actionListener) {
        eliminarIntegranteBtn.addActionListener(actionListener);
    }

    public void clearFields() {
        fieldNombreEquipo.setText("");
        fieldCorreo.setText("");
        fieldNuevoIntegrante.setText("");
        integrantesModel.clear();
        updateIntegrantesCountLabel(); // Actualiza la etiqueta después de limpiar
    }

    public void clearNuevoIntegranteField() {
        fieldNuevoIntegrante.setText("");
    }
}
