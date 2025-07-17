package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel de formulario para inscribir equipos en un torneo.
 * Permite ingresar nombre del equipo, añadir/eliminar integrantes y especificar un correo de contacto.
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

    /**
     * Constructor que configura el panel y todos sus componentes de formulario
     * para la inscripción de equipos.
     */
    public PanelFormularioEquipo() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);

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
        fieldNombreEquipo.setPreferredSize(fieldSize);
        add(fieldNombreEquipo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 10, 5);
        JLabel labelIntegrantesTitulo = new JLabel("Integrantes del Equipo:");
        labelIntegrantesTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        labelIntegrantesTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelIntegrantesTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 5);
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

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        contadorIntegrantesLabel = new JLabel("Número de Integrantes: 0 (Máx: -)");
        contadorIntegrantesLabel.setFont(labelFont);
        add(contadorIntegrantesLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 15, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel labelDatosContacto = new JLabel("Datos de Contacto:");
        labelDatosContacto.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(labelDatosContacto, gbc);

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

        JPanel correoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        correoPanel.setOpaque(false);
        fieldCorreo = new JTextField(10);
        fieldCorreo.setFont(fieldFont);
        fieldCorreo.setPreferredSize(new Dimension(fieldSize.width - 80, preferredHeight));
        correoPanel.add(fieldCorreo);
        JLabel gmailLabel = new JLabel("@gmail.com");
        gmailLabel.setFont(fieldFont);
        correoPanel.add(gmailLabel);
        add(correoPanel, gbc);
    }

    /**
     * Establece el número máximo de integrantes permitidos y actualiza la etiqueta de conteo.
     * @param maxIntegrantes El número máximo de integrantes.
     */
    public void setMaxIntegrantes(int maxIntegrantes) {
        this.maxIntegrantes = maxIntegrantes;
        updateIntegrantesCountLabel();
    }

    /**
     * Actualiza el texto de la etiqueta que muestra el número actual de integrantes y el máximo.
     */
    public void updateIntegrantesCountLabel() {
        if (maxIntegrantes > 0) {
            contadorIntegrantesLabel.setText("Integrantes: " + integrantesModel.size() + " (Máx: " + maxIntegrantes + ")");
        }
        else {
            contadorIntegrantesLabel.setText("Integrantes: " + integrantesModel.size());
        }
    }

    /**
     * Obtiene el nombre del equipo ingresado en el formulario.
     * @return El nombre del equipo.
     */
    public String getNombreEquipo() {
        return fieldNombreEquipo.getText().trim();
    }

    /**
     * Obtiene el correo electrónico ingresado, asegurándose de que termine en "@gmail.com" si no tiene un dominio.
     * @return El correo electrónico del equipo.
     */
    public String getCorreo() {
        String correo = fieldCorreo.getText().trim();
        if (!correo.isEmpty() && !correo.contains("@") && !correo.endsWith("@gmail.com")) {
            correo += "@gmail.com";
        } else if (!correo.isEmpty() && !correo.endsWith("@gmail.com") && !correo.contains("@")) {
            correo += "@gmail.com";
        }
        return correo;
    }

    /**
     * Obtiene la lista actual de integrantes ingresados en el formulario.
     * @return Una lista de los nombres de los integrantes.
     */
    public List<String> getIntegrantes() {
        List<String> integrantes = new ArrayList<>();
        for (int i=0; i < integrantesModel.size(); i++) {
            integrantes.add(integrantesModel.getElementAt(i));
        }
        return integrantes;
    }

    /**
     * Obtiene el texto del campo para añadir un nuevo integrante.
     * @return El nombre del nuevo integrante.
     */
    public String getNuevoIntegrante() {
        return fieldNuevoIntegrante.getText();
    }

    /**
     * Obtiene el índice del integrante seleccionado actualmente en la lista.
     * @return El índice del elemento seleccionado, o -1 si no hay selección.
     */
    public int getSelectedIndexIntegrantesList() {
        return integrantesList.getSelectedIndex();
    }

    /**
     * Obtiene el modelo de lista que contiene los integrantes.
     * @return El modelo de lista de integrantes.
     */
    public DefaultListModel<String> getIntegrantesModel() {
        return integrantesModel;
    }

    /**
     * Añade un ActionListener al boton "Agregar Integrante".
     * @param actionListener El listener a añadir.
     */
    public void addAgregarIntegranteListener(ActionListener actionListener) {
        agregarIntegranteBtn.addActionListener(actionListener);
    }

    /**
     * Añade un ActionListener al boton "Eliminar Integrante".
     * @param actionListener El listener a añadir.
     */
    public void addEliminarIntegranteListener(ActionListener actionListener) {
        eliminarIntegranteBtn.addActionListener(actionListener);
    }

    public void clearFields() {
        fieldNombreEquipo.setText("");
        fieldCorreo.setText("");
        fieldNuevoIntegrante.setText("");
        integrantesModel.clear();
        updateIntegrantesCountLabel();
    }

    /**
     * Limpia todos los campos del formulario y la lista de integrantes.
     */
    public void clearNuevoIntegranteField() {
        fieldNuevoIntegrante.setText("");
    }
}
