package org.example.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}

// Distincion de roles Usuario/Organizador

abstract class Rango {
    protected String name;

    public Rango(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public abstract String getRolDisplay();
}

class Usuario extends Rango {
    public Usuario(String name) {
        super(name);
    }
    @Override
    public String getRolDisplay() {
        return "Usuario";
    }
}

class Organizador extends Rango {
    public Organizador(String name) {
        super(name);
    }
    @Override
    public String getRolDisplay() {
        return "Organizador";
    }
}

// Panel generico para crear un boton
class PanelButton extends JButton {
    public PanelButton(String label, Font font) {
        super(label);
        this.setFont(font);
    }
}

// Navegador para manejar el historial de acciones del Usuario/Organizador
class Navegador {
    private PanelPrincipal panelPrincipal;
    private Stack<ActionListener> historial;

    // actionAssigner es un objeto que implementa ActionAssigner
    private ActionAssigner actionAssigner;

    public Navegador(PanelPrincipal panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
        this.historial = new Stack<>();
        this.actionAssigner = new AbstractActionAssigner(this) {};
    }

    public void mostrarPanelInicio() {
        // El historial se limpia por completo si se vuelve al inicio
        historial.clear();
        PanelInicio panelInicio = panelPrincipal.getPanel(PanelID.INICIO.getID(), PanelInicio.class);

        // Inicializar
        panelInicio.inicializar(actionAssigner);
        panelPrincipal.cambiarPanel(panelInicio);
    }

    public void mostrarPanelUsuario() {
        historial.push(e -> mostrarPanelInicio());
        PanelUsuario panelUsuario = panelPrincipal.getPanel(PanelID.USUARIO.getID(), PanelUsuario.class);

        // Inicializar
        panelUsuario.inicializar(actionAssigner);
        panelPrincipal.cambiarPanel(panelUsuario);
    }

    public void mostrarPanelEstadoActualTorneo() {
        historial.push(e -> actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));
        PanelEstadoActualTorneo panelEstadoActualTorneo = panelPrincipal.getPanel(PanelID.ESTADO_ACTUAL.getID(), PanelEstadoActualTorneo.class);

        // Inicializar
        panelEstadoActualTorneo.inicializar(actionAssigner);
        panelPrincipal.cambiarPanel(panelEstadoActualTorneo);
    }

    public void mostrarPanelOrganizador() {
        historial.push(e -> mostrarPanelInicio());
        PanelOrganizador panelOrganizador = panelPrincipal.getPanel(PanelID.ORGANIZADOR.getID(), PanelOrganizador.class);

        // Inicializar
        panelOrganizador.inicializar(actionAssigner);
        panelPrincipal.cambiarPanel(panelOrganizador);
    }

    public void mostrarPanelCrearTorneo() {
        historial.push(e -> mostrarPanelOrganizador());
        PanelCrearTorneo panelCrearTorneo = panelPrincipal.getPanel(PanelID.CREAR_TORNEO.getID(), PanelCrearTorneo.class);

        // Inicializar
        panelCrearTorneo.inicializar(actionAssigner);
        panelPrincipal.cambiarPanel(panelCrearTorneo);
    }

    public void mostrarPanelInscribirParticipantes() {
        historial.push(e -> mostrarPanelOrganizador());
        PanelInscribirParticipantes panelInscribirParticipantes = panelPrincipal.getPanel(PanelID.INSCRIBIR_PARTICIPANTES.getID(), PanelInscribirParticipantes.class);

        panelInscribirParticipantes.inicializar(actionAssigner);
        panelPrincipal.cambiarPanel(panelInscribirParticipantes);
    }

    public void mostrarPanelIniciarTorneo() {
        historial.push(e -> mostrarPanelOrganizador());
        PanelIniciarTorneo panelIniciarTorneo = panelPrincipal.getPanel(PanelID.INICIAR_TORNEO.getID(), PanelIniciarTorneo.class);

        panelIniciarTorneo.inicializar(actionAssigner);
        panelPrincipal.cambiarPanel(panelIniciarTorneo);
    }
}

// Ventana principal

class VentanaPrincipal extends JFrame {
    private PanelPrincipal panelPrincipal;
    private Navegador navegador;

    public VentanaPrincipal() {
        setTitle("Gestión de Torneos");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelPrincipal = new PanelPrincipal();

        // Instanciar el navegador
        navegador = new Navegador(panelPrincipal);

        // Al iniciar, siempre mostrar el PanelInicio
        navegador.mostrarPanelInicio();

        add(panelPrincipal);
        setVisible(true);
    }
}