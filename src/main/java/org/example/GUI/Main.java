package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}

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

interface PanelPrincipal {
    JPanel getPanel();
    void inicializar(Rango rango, ActionListener irAtras, ActionListener irAdelante);
}

class PanelUsuario implements PanelPrincipal {
    private JPanel panel;
    private JButton volverInicioBoton;

    public PanelUsuario() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(200, 200, 255));

        // Colocacion de botones
        volverInicioBoton = new JButton("Volver al Inicio");

        JPanel southPanel = new JPanel();
        southPanel.setBackground(panel.getBackground());
        southPanel.add(volverInicioBoton);
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(southPanel, BorderLayout.SOUTH);

    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void inicializar(Rango usuario, ActionListener irAInicio, ActionListener irAdelante) {

        JLabel rolLabel = new JLabel("Estás en el panel de: " + usuario.getRolDisplay(), SwingConstants.CENTER);
        rolLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(rolLabel, BorderLayout.CENTER);

        for (ActionListener al : volverInicioBoton.getActionListeners()) {
            volverInicioBoton.removeActionListener(al);
        }
        if (irAInicio != null) {
            volverInicioBoton.addActionListener(irAInicio);
        }

        panel.revalidate();
        panel.repaint();
    }
}

class PanelOrganizador implements PanelPrincipal {
    private JPanel panel;
    private JButton crearTorneoBoton;
    private JButton volverInicioBoton;

    public PanelOrganizador() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(200, 255, 200));

        crearTorneoBoton = new JButton("Crear Torneo");
        crearTorneoBoton.setAlignmentX(Component.CENTER_ALIGNMENT);
        volverInicioBoton = new JButton("Volver al Inicio");

        // Colocacion
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(panel.getBackground());
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        centerPanel.add(crearTorneoBoton);
        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(panel.getBackground());
        southPanel.add(volverInicioBoton);

        panel.add(southPanel, BorderLayout.SOUTH);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void inicializar(Rango organizador, ActionListener irAInicio, ActionListener crearTorneoAL) {

        for (ActionListener al : volverInicioBoton.getActionListeners()) {
            volverInicioBoton.removeActionListener(al);
        }
        if (irAInicio != null) {
            volverInicioBoton.addActionListener(irAInicio);
        }

        for (ActionListener al : crearTorneoBoton.getActionListeners()) {
            crearTorneoBoton.removeActionListener(al);
        }
        if (crearTorneoAL != null) {
            crearTorneoBoton.addActionListener(crearTorneoAL);
        }

        panel.revalidate();
        panel.repaint();
    }
}

class PanelCrearTorneo implements PanelPrincipal {
    private JPanel panel;
    private JButton volverBoton;
    private JButton pulsarParaCrearBoton;

    public PanelCrearTorneo() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 200));

        JLabel titleLabel = new JLabel("Panel: Crear Torneo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        panel.add(titleLabel, BorderLayout.CENTER);

        volverBoton = new JButton("Volver atrás");
        pulsarParaCrearBoton = new JButton("Pulsar para crear");

        JPanel southPanel = new JPanel();
        southPanel.setBackground(panel.getBackground());
        southPanel.add(volverBoton);
        panel.add(southPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(panel.getBackground());
        centerPanel.add(pulsarParaCrearBoton);
        panel.add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void inicializar(Rango rango, ActionListener irAtras, ActionListener irAdelante) {
        for (ActionListener al : volverBoton.getActionListeners()) {
            volverBoton.removeActionListener(al);
        }
        if (irAtras != null) {
            volverBoton.addActionListener(irAtras);
        }
        panel.revalidate();
        panel.repaint();
    }
}

class VentanaPrincipal extends JFrame {
    private JPanel panelActual;
    private JTextField tipoDeRango;
    private JPanel panelInicio;

    private PanelOrganizador panelOrganizador;
    private PanelUsuario panelUsuario;
    private PanelCrearTorneo panelCrearTorneo;

    private Navegador navegador;

    public VentanaPrincipal() {
        setTitle("Gestión de Torneos");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelOrganizador = new PanelOrganizador();
        panelUsuario = new PanelUsuario();
        panelCrearTorneo = new PanelCrearTorneo();

        // Panel de inicio
        panelInicio = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 100));
        panelInicio.setBackground(Color.LIGHT_GRAY);
        panelInicio.add(new JLabel("Ingresa tu rol (Organizador/Usuario):"));
        tipoDeRango = new JTextField(15);
        panelInicio.add(tipoDeRango);
        JButton loginBoton = new JButton("Login");
        panelInicio.add(loginBoton);

        // Instanciar el navegador
        navegador = new Navegador(this,
                panelInicio,
                panelOrganizador,
                panelUsuario,
                panelCrearTorneo
        );

        // Listener para el boton de login
        loginBoton.addActionListener(e -> {
            String rol = tipoDeRango.getText().trim();

            if ("Organizador".equalsIgnoreCase(rol)) {
                navegador.mostrarPanelOrganizador(new Organizador("El Admin"));
            }
            else if ("Usuario".equalsIgnoreCase(rol)) {
                navegador.mostrarPanelUsuario(new Usuario("El no Admin"));
            }
            else {
                JOptionPane.showMessageDialog(this, "Rol no válido.");
            }
            tipoDeRango.setText("");
        });

        // Al iniciar, siempre mostrar el panel inicial
        navegador.mostrarPanelInicio();
    }

    public void cambiarPanel(JPanel nuevoPanel) {
        if (panelActual != null) {
            remove(panelActual);
        }

        panelActual = nuevoPanel;
        add(panelActual, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}

class Navegador {
    private VentanaPrincipal ventanaPrincipal;

    // Paneles
    private JPanel panelInicio;
    private PanelOrganizador panelOrganizador;
    private PanelUsuario panelUsuario;
    private PanelCrearTorneo panelCrearTorneo;

    // Historial de navegacion del Usuario/Organizador
    private Stack<Runnable> historial;

    public Navegador(VentanaPrincipal ventanaPrincipal,
                     JPanel panelInicio,
                     PanelOrganizador panelOrganizador,
                     PanelUsuario panelUsuario,
                     PanelCrearTorneo panelCrearTorneo) {

        this.ventanaPrincipal = ventanaPrincipal;
        this.panelInicio = panelInicio;
        this.panelOrganizador = panelOrganizador;
        this.panelUsuario = panelUsuario;
        this.panelCrearTorneo = panelCrearTorneo;

        this.historial = new Stack<>();
    }

    public void mostrarPanelInicio() {
        // El historial se limpia por completo si se vuelve al inicio
        historial.clear();
        ventanaPrincipal.cambiarPanel(panelInicio);
    }

    public void mostrarPanelUsuario(Rango rango) {
        historial.push(() -> mostrarPanelUsuario(rango));

        ActionListener irAInicioAction = e -> mostrarPanelInicio();
        panelUsuario.inicializar(rango, irAInicioAction, null);
        ventanaPrincipal.cambiarPanel(panelUsuario.getPanel());
    }

    public void mostrarPanelOrganizador(Rango rango) {
        historial.push(() -> mostrarPanelOrganizador(rango));

        ActionListener irAInicioAction = e -> mostrarPanelInicio();
        ActionListener crearTorneoAction = e -> mostrarPanelCrearTorneo(rango);
        panelOrganizador.inicializar(rango, irAInicioAction, crearTorneoAction);
        ventanaPrincipal.cambiarPanel(panelOrganizador.getPanel());
    }

    public void mostrarPanelCrearTorneo(Rango rango) {
        historial.push(() -> mostrarPanelOrganizador(rango));

        ActionListener irAtrasAction = e -> irAPanelAnterior();
        panelCrearTorneo.inicializar(rango, irAtrasAction, null);
        ventanaPrincipal.cambiarPanel(panelCrearTorneo.getPanel());
    }

    public void irAPanelAnterior() {
        if (!historial.empty()) {
            historial.pop().run();
        }
        else {
            mostrarPanelInicio();
        }
    }
}

