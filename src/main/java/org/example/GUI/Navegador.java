package org.example.GUI;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Navegador {
    private PanelPrincipal panelPrincipal;
    private Stack<ActionListener> historial;

    // actionAssigner es un objeto que implementa ActionAssigner
    private ActionAssigner actionAssigner;

    public Navegador(PanelPrincipal panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
        this.historial = new Stack<>();
        this.actionAssigner = new AbstractActionAssigner(this) {};
    }

    private <T extends JPanel & PanelConfigurable> void mostrarPanel(
            PanelType panelType,
            ActionListener actionToPushHistorial) {

        if(actionToPushHistorial != null) {
            historial.push(actionToPushHistorial);
        }

        T panel = panelPrincipal.getPanel(panelType);
        panel.inicializar(actionAssigner);
        panelPrincipal.cambiarPanel(panel);
    }

    public void mostrarPanelInicio() {
        // El historial se limpia por completo si se vuelve al inicio
        historial.clear();
        PanelInicio panelInicio = panelPrincipal.getPanel(PanelType.INICIO);

        // Inicializar
        panelInicio.inicializar(actionAssigner);
        panelPrincipal.cambiarPanel(panelInicio);
    }

    public void mostrarPanelUsuario() {
        mostrarPanel(PanelType.USUARIO, e -> mostrarPanelInicio());
    }
    public void mostrarPanelEstadoActualTorneo() {
        mostrarPanel(PanelType.ESTADO_ACTUAL, e -> mostrarPanelUsuario());
    }
    public void mostrarPanelProximosEncuentros() {
        mostrarPanel(PanelType.PROXIMOS_ENCUENTROS, e -> mostrarPanelUsuario());
    }
    public void mostrarPanelEstadisticasGenerales() {
        mostrarPanel(PanelType.ESTADISTICAS_GENERALES, e -> mostrarPanelUsuario());
    }
    public void mostrarPanelOrganizador() {
        mostrarPanel(PanelType.ORGANIZADOR, e -> mostrarPanelInicio());
    }
    public void mostrarPanelCrearTorneo() {
        mostrarPanel(PanelType.CREAR_TORNEO, e -> mostrarPanelOrganizador());
    }
    public void mostrarPanelInscribirParticipantes() {
        mostrarPanel(PanelType.INSCRIBIR_PARTICIPANTES, e -> mostrarPanelOrganizador());
    }
    public void mostrarPanelIniciarTorneo() {
        mostrarPanel(PanelType.INICIAR_TORNEO, e -> mostrarPanelOrganizador());
    }
    public void mostrarPanelRegistrarResultados() {
        mostrarPanel(PanelType.REGISTRAR_RESULTADOS, e -> mostrarPanelOrganizador());
    }
}
