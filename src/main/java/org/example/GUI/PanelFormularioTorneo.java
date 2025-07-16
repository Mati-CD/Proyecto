package org.example.GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Panel de formulario para la creación de un torneo.
 * Incluye campos para el nombre, disciplina, tipo de inscripción y número de participantes.
 */
public class PanelFormularioTorneo extends JPanel {
    private JTextField fieldNombre;
    private JTextField fieldDisciplina;
    private JComboBox<String> comboTipoInscripcion;
    private JComboBox<Integer> comboNumParticipantes;
    private JTextField fieldNumMiembrosEquipo;
    private JLabel labelNumMiembrosEquipo;

    private final Dimension fieldYComboSize = new Dimension(280, 30);
    private final Font tittleFont = new Font("SansSerif", Font.BOLD, 20);
    private final Font componentFont = new Font("SansSerif", Font.BOLD, 18);
    private final Color labelColor = Color.WHITE;

    /**
     * Constructor que inicializa y dispone gráficamente los campos del formulario.
     */
    public PanelFormularioTorneo() {
        setOpaque(false);
        setLayout(new GridLayout(0, 2, 15, 80));

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                "Datos del Torneo",
                TitledBorder.CENTER, TitledBorder.TOP,
                tittleFont, Color.WHITE
        );

        setBorder(BorderFactory.createCompoundBorder(
                titledBorder,
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Inicializar componentes
        JLabel labelNombre = new JLabel("Nombre del Torneo:");
        labelNombre.setFont(componentFont);
        GuiUtils.setLabelTextColor(labelNombre, labelColor);

        fieldNombre = new JTextField();
        fieldNombre.setFont(componentFont);
        fieldNombre.setPreferredSize(fieldYComboSize);
        fieldNombre.setMaximumSize(fieldYComboSize);
        fieldNombre.setForeground(Color.BLACK);
        fieldNombre.setBackground(Color.WHITE);
        fieldNombre.setCaretColor(Color.BLACK);

        JLabel labelDisciplina = new JLabel("Disciplina:");
        labelDisciplina.setFont(componentFont);
        GuiUtils.setLabelTextColor(labelDisciplina, labelColor); // <--- USAR EL MÉTODO DE UTILIDAD
        fieldDisciplina = new JTextField();
        fieldDisciplina.setFont(componentFont);
        fieldDisciplina.setPreferredSize(fieldYComboSize);
        fieldDisciplina.setMaximumSize(fieldYComboSize);
        fieldDisciplina.setForeground(Color.BLACK);
        fieldDisciplina.setBackground(Color.WHITE);
        fieldDisciplina.setCaretColor(Color.BLACK);


        JLabel labelNumParticipantes = new JLabel("Número de Participantes:");
        labelNumParticipantes.setFont(componentFont);
        GuiUtils.setLabelTextColor(labelNumParticipantes, labelColor); // <--- USAR EL MÉTODO DE UTILIDAD
        Integer[] numParticipantesOpciones = {2, 4, 8, 16, 32};
        comboNumParticipantes = new JComboBox<>(numParticipantesOpciones);
        comboNumParticipantes.setFont(componentFont);
        comboNumParticipantes.setPreferredSize(fieldYComboSize);
        comboNumParticipantes.setMaximumSize(fieldYComboSize);
        comboNumParticipantes.setSelectedIndex(0);
        comboNumParticipantes.setForeground(Color.BLACK); // Texto seleccionado en el combo
        comboNumParticipantes.setBackground(Color.WHITE); // Fondo del combo


        JLabel labelTipoInscripcion = new JLabel("Tipo de Inscripción:");
        labelTipoInscripcion.setFont(componentFont);
        GuiUtils.setLabelTextColor(labelTipoInscripcion, labelColor); // <--- USAR EL MÉTODO DE UTILIDAD
        String[] tiposDeInscripcion = {"Individual", "Equipo"};
        comboTipoInscripcion = new JComboBox<>(tiposDeInscripcion);
        comboTipoInscripcion.setFont(componentFont);
        comboTipoInscripcion.setPreferredSize(fieldYComboSize);
        comboTipoInscripcion.setMaximumSize(fieldYComboSize);
        comboTipoInscripcion.setSelectedIndex(0);
        comboTipoInscripcion.setForeground(Color.BLACK);
        comboTipoInscripcion.setBackground(Color.WHITE);


        labelNumMiembrosEquipo = new JLabel("Miembros por Equipo:");
        labelNumMiembrosEquipo.setFont(componentFont);
        GuiUtils.setLabelTextColor(labelNumMiembrosEquipo, labelColor); // <--- USAR EL MÉTODO DE UTILIDAD
        fieldNumMiembrosEquipo = new JTextField();
        fieldNumMiembrosEquipo.setFont(componentFont);
        fieldNumMiembrosEquipo.setPreferredSize(fieldYComboSize);
        fieldNumMiembrosEquipo.setMaximumSize(fieldYComboSize);
        fieldNumMiembrosEquipo.setForeground(Color.BLACK);
        fieldNumMiembrosEquipo.setBackground(Color.WHITE);
        fieldNumMiembrosEquipo.setCaretColor(Color.BLACK);
        labelNumMiembrosEquipo.setVisible(false);
        fieldNumMiembrosEquipo.setVisible(false);

        // ... (resto de los wrappers y el ActionListener) ...
        JPanel labelNombreWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        labelNombreWrapper.setOpaque(false);
        labelNombreWrapper.add(labelNombre);
        add(labelNombreWrapper);

        JPanel fieldNombreWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        fieldNombreWrapper.setOpaque(false);
        fieldNombreWrapper.add(fieldNombre);
        add(fieldNombreWrapper);

        JPanel labelDisciplinaWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        labelDisciplinaWrapper.setOpaque(false);
        labelDisciplinaWrapper.add(labelDisciplina);
        add(labelDisciplinaWrapper);

        JPanel fieldDisciplinaWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        fieldDisciplinaWrapper.setOpaque(false);
        fieldDisciplinaWrapper.add(fieldDisciplina);
        add(fieldDisciplinaWrapper);

        JPanel labelNumParticipantesWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        labelNumParticipantesWrapper.setOpaque(false);
        labelNumParticipantesWrapper.add(labelNumParticipantes);
        add(labelNumParticipantesWrapper);

        JPanel comboNumParticipantesWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        comboNumParticipantesWrapper.setOpaque(false);
        comboNumParticipantesWrapper.add(comboNumParticipantes);
        add(comboNumParticipantesWrapper);

        JPanel labelTipoInscripcionWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        labelTipoInscripcionWrapper.setOpaque(false);
        labelTipoInscripcionWrapper.add(labelTipoInscripcion);
        add(labelTipoInscripcionWrapper);

        JPanel comboTipoInscripcionWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        comboTipoInscripcionWrapper.setOpaque(false);
        comboTipoInscripcionWrapper.add(comboTipoInscripcion);
        add(comboTipoInscripcionWrapper);

        JPanel currentLabelWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        currentLabelWrapper.setOpaque(false);
        currentLabelWrapper.add(labelNumMiembrosEquipo);
        add(currentLabelWrapper);

        JPanel currentFieldWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        currentFieldWrapper.setOpaque(false);
        currentFieldWrapper.add(fieldNumMiembrosEquipo);
        add(currentFieldWrapper);

        comboTipoInscripcion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarVisibilidadMiembrosEquipo();
            }
        });
    }

    /**
     * Actualiza la visibilidad del campo de miembros por equipo, esto
     * controlado por el tipo de inscripción seleccionado.
     */
    private void actualizarVisibilidadMiembrosEquipo() {
        String tipoInscripcion = (String) comboTipoInscripcion.getSelectedItem();
        boolean esEquipo = "Equipo".equals(tipoInscripcion);
        labelNumMiembrosEquipo.setVisible(esEquipo);
        fieldNumMiembrosEquipo.setVisible(esEquipo);

        revalidate();
        repaint();
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

    public int getNumMiembrosEquipo() {
        if ("Individual".equals(getTipoInscripcion())) {
            return 0;
        }
        try {
            return Integer.parseInt(fieldNumMiembrosEquipo.getText().trim());
        } catch (NumberFormatException e) {

            return 0;
        }
    }
    /**
     * Limpia todos los campos del formulario, restableciendo sus valores por defecto.
     */
    public void clearFields() {
        fieldNombre.setText("");
        fieldDisciplina.setText("");
        comboTipoInscripcion.setSelectedIndex(0);
        comboNumParticipantes.setSelectedIndex(0);
        fieldNumMiembrosEquipo.setText("");
        actualizarVisibilidadMiembrosEquipo();
    }
}