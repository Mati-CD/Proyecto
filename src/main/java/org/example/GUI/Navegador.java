package org.example.GUI;

import org.example.CodigoLogico.GestorTorneos;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Stack;

/**
 * Clase que controla la navegación entre distintos paneles de la interfaz gráfica.
 */
public class Navegador {

    private PanelPrincipal panelPrincipal;
    private Stack<ActionListener> historial;
    private ActionAssigner actionAssigner;
    private GestorTorneos gestorTorneos;

    /**
     * @param panelPrincipal el panel principal que contiene todos los subpaneles navegables
     */
    public Navegador(PanelPrincipal panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
        this.historial = new Stack<>();
        this.actionAssigner = new AbstractActionAssigner(this) {};
        this.gestorTorneos = new GestorTorneos();
    }

    /**
     * Muestra un panel específico en la interfaz y actualiza el historial de navegación.
     *
     * @param panelType el tipo de panel a mostrar
     * @param actionToPushHistorial la acción a guardar en el historial para retroceder
     * @param <T> tipo del panel que debe extenderse de JPanel e implementar PanelConfigurable
     */
    private <T extends JPanel & PanelConfigurable> void mostrarPanel(
            PanelType panelType,
            ActionListener actionToPushHistorial) {

        if (actionToPushHistorial != null) {
            historial.push(actionToPushHistorial);
        }

        T panel = panelPrincipal.getPanel(panelType);
        panel.inicializar(actionAssigner, gestorTorneos);
        panelPrincipal.cambiarPanel(panel);
    }

    /**
     * Muestra el panel de inicio y limpia completamente el historial de navegación.
     */
    public void mostrarPanelInicio() {
        historial.clear();
        PanelInicio panelInicio = panelPrincipal.getPanel(PanelType.INICIO);
        panelInicio.inicializar(actionAssigner, gestorTorneos);
        panelPrincipal.cambiarPanel(panelInicio);
    }

    /** Muestra el panel de usuario. */
    public void mostrarPanelUsuario() {
        mostrarPanel(PanelType.USUARIO, e -> mostrarPanelInicio());
    }

    /** Muestra el panel de estado actual del torneo. */
    public void mostrarPanelEstadoActualTorneo() {
        mostrarPanel(PanelType.ESTADO_ACTUAL, e -> mostrarPanelUsuario());
    }

    /** Muestra el panel de próximos encuentros. */
    public void mostrarPanelProximosEncuentros() {
        mostrarPanel(PanelType.PROXIMOS_ENCUENTROS, e -> mostrarPanelUsuario());
    }

    /** Muestra el panel de estadísticas generales. */
    public void mostrarPanelEstadisticasGenerales() {
        mostrarPanel(PanelType.ESTADISTICAS_GENERALES, e -> mostrarPanelUsuario());
    }

    /** Muestra el panel para ver participantes. */
    public void mostrarPanelVerParticipantes() {
        mostrarPanel(PanelType.VER_PARTICIPANTES, e -> mostrarPanelUsuario());
    }

    /** Muestra el panel principal del organizador. */
    public void mostrarPanelOrganizador() {
        mostrarPanel(PanelType.ORGANIZADOR, e -> mostrarPanelInicio());
    }

    /** Muestra el panel de creación de torneo. */
    public void mostrarPanelCrearTorneo() {
        mostrarPanel(PanelType.CREAR_TORNEO, e -> mostrarPanelOrganizador());
    }

    /** Muestra el panel para inscribir participantes. */
    public void mostrarPanelInscribirParticipantes() {
        mostrarPanel(PanelType.INSCRIBIR_PARTICIPANTES, e -> mostrarPanelOrganizador());
    }

    /** Muestra el panel para iniciar el torneo. */
    public void mostrarPanelIniciarTorneo() {
        mostrarPanel(PanelType.INICIAR_TORNEO, e -> mostrarPanelOrganizador());
    }

    /** Muestra el panel para registrar resultados. */
    public void mostrarPanelRegistrarResultados() {
        mostrarPanel(PanelType.REGISTRAR_RESULTADOS, e -> mostrarPanelOrganizador());
    }
}