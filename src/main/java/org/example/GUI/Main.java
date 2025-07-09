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

// Paneles con los Actions que se ejecutaran al pulsar "x" boton

interface CrearActions {
    ActionListener getAction(String actionID);
}

class InicioActions implements CrearActions {
    private Navegador navegador;
    private PanelInicio panelInicio;

    public InicioActions(Navegador navegador, PanelInicio panelInicio) {
        this.navegador = navegador;
        this.panelInicio = panelInicio;
    }

    @Override
    public ActionListener getAction(String actionID) {
        switch (actionID) {
            case "LOGIN":
                return e -> {
                    String rol = panelInicio.getTipoDeRango().getText().trim();
                    if ("Organizador".equalsIgnoreCase(rol)) {
                        navegador.mostrarPanelOrganizador(new Organizador("El admin"));
                    }
                    else if ("Usuario".equalsIgnoreCase(rol)) {
                        navegador.mostrarPanelUsuario(new Usuario("El no admin"));
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Rol no válido.");
                    }
                };
            default:
                return null;
        }
    }
}

class UsuarioActions implements CrearActions {
    private Navegador navegador;

    public UsuarioActions(Navegador navegador) {
        this.navegador = navegador;
    }

    @Override
    public ActionListener getAction(String actionID) {
        switch (actionID) {
            case "IR_A_INICIO":
                return e -> navegador.mostrarPanelInicio();
            default:
                return null;
        }
    }
}

class OrganizadorActions implements CrearActions {
    private Navegador navegador;
    private Rango rango;

    public OrganizadorActions(Navegador navegador, Rango rango) {
        this.rango = rango;
        this.navegador = navegador;
    }

    @Override
    public ActionListener getAction(String actionID) {
        switch (actionID) {
            case "IR_A_INICIO":
                return e -> navegador.mostrarPanelInicio();
            case "CREAR_TORNEO":
                return e -> navegador.mostrarPanelCrearTorneo(rango);
            default:
                return null;
        }
    }
}

class CrearTorneoActions implements CrearActions {
    private Navegador navegador;
    private Rango rango;

    public CrearTorneoActions(Navegador navegador, Rango rango) {
        this.navegador = navegador;
        this.rango = rango;
    }

    @Override
    public ActionListener getAction(String actionID) {
        switch (actionID) {
            case "IR_ATRAS":
                return e -> navegador.mostrarPanelOrganizador(rango);
            default:
                return null;
        }
    }
}

// Panel generico para crear un boton

class PanelButton extends JButton {
    public PanelButton(String label, Font font) {
        super(label);
        this.setFont(font);
    }
}

// Paneles con el grupo de botones que seran asignados a un panel en especifico

interface PanelButtonsGroup {
    void setNavegadorActions(CrearActions actions);
}

class PanelInicioButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton loginBtn;

    public PanelInicioButtons() {
        setLayout(new BorderLayout());
        Font font = new Font("SansSerif", Font.BOLD, 18);
        loginBtn = new PanelButton("Login", font);
        add(loginBtn);
    }

    @Override
    public void setNavegadorActions(CrearActions actions) {
        loginBtn.addActionListener(actions.getAction("LOGIN"));
    }
}

class PanelOrganizadorButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton irAInicioBtn;
    private PanelButton crearTorneoBtn;
    private PanelButton inscribirParticipantesBtn;
    private PanelButton iniciarTorneoBtn;
    private PanelButton actualizarRegistroDeResultadosBtn;

    public PanelOrganizadorButtons() {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 255, 200));

        Font font = new Font("SansSerif", Font.BOLD, 18);
        Font font1 = new Font("Arial", Font.BOLD, 12);

        // Crear botones
        irAInicioBtn = new PanelButton("Volver al Inicio", font1);
        crearTorneoBtn = new PanelButton("Crear Torneo", font);
        inscribirParticipantesBtn = new PanelButton("Inscribir Participantes", font);
        iniciarTorneoBtn = new PanelButton("Iniciar Torneo", font);
        actualizarRegistroDeResultadosBtn = new PanelButton("Actualizar Registro de Resultados", font);

        // Posicionar botones
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAInicioBtn);
        add(topLeftPanel, BorderLayout.NORTH);

        JPanel columnaButtonsPanel = new JPanel(new GridLayout(4, 1, 0, 100));
        columnaButtonsPanel.setOpaque(false);
        columnaButtonsPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        columnaButtonsPanel.add(crearTorneoBtn);
        columnaButtonsPanel.add(inscribirParticipantesBtn);
        columnaButtonsPanel.add(iniciarTorneoBtn);
        columnaButtonsPanel.add(actualizarRegistroDeResultadosBtn);

        JPanel centerLeftButtonsPanel = new JPanel(new BorderLayout());
        centerLeftButtonsPanel.setOpaque(false);
        centerLeftButtonsPanel.add(columnaButtonsPanel, BorderLayout.WEST);
        add(centerLeftButtonsPanel, BorderLayout.CENTER);
    }

    @Override
    public void setNavegadorActions(CrearActions actions) {
        irAInicioBtn.addActionListener(actions.getAction("IR_A_INICIO"));
        crearTorneoBtn.addActionListener(actions.getAction("CREAR_TORNEO"));
    }
}

// Clase temporal para el prototipo
class PanelCrearTorneoButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton irAtrasBtn;

    public PanelCrearTorneoButtons() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 255, 200));

        Font font = new Font("SansSerif", Font.BOLD, 18);

        // Crear Botones
        irAtrasBtn = new PanelButton("Volver atrás", font);

        irAtrasBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(irAtrasBtn);
    }

    @Override
    public void setNavegadorActions(CrearActions actions) {
        irAtrasBtn.addActionListener(actions.getAction("IR_ATRAS"));
    }
}

class PanelUsuarioButtons extends JPanel implements PanelButtonsGroup{
    private PanelButton irAInicioBtn;
    private PanelButton verEstadoActualTorneoBtn;
    private PanelButton verProxEncuentrosBtn;
    private PanelButton verEstadisticasBtn;

    public PanelUsuarioButtons() {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 200, 255));

        Font font = new Font("SansSerif", Font.BOLD, 18);
        Font font1 = new Font("Arial", Font.BOLD, 12);

        // Crear Botones
        irAInicioBtn = new PanelButton("Volver atrás", font1);
        verEstadoActualTorneoBtn = new PanelButton("Ver estado actual del Torneo", font);
        verProxEncuentrosBtn = new PanelButton("Ver próximos encuentros", font);
        verEstadisticasBtn = new PanelButton("Ver estadisticas del Torneo", font);

        // Posicionar botones
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(irAInicioBtn);
        add(topLeftPanel, BorderLayout.NORTH);

        JPanel columnaButtonsPanel = new JPanel(new GridLayout(4, 1, 0, 100));
        columnaButtonsPanel.setOpaque(false);
        columnaButtonsPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 60, 0));

        columnaButtonsPanel.add(verEstadoActualTorneoBtn);
        columnaButtonsPanel.add(verProxEncuentrosBtn);
        columnaButtonsPanel.add(verEstadisticasBtn);

        JPanel centerLeftButtonsPanel = new JPanel(new BorderLayout());
        centerLeftButtonsPanel.setOpaque(false);
        centerLeftButtonsPanel.add(columnaButtonsPanel, BorderLayout.WEST);
        add(centerLeftButtonsPanel, BorderLayout.CENTER);
    }

    @Override
    public void setNavegadorActions(CrearActions actions) {
        irAInicioBtn.addActionListener(actions.getAction("IR_A_INICIO"));
    }
}

// Configurar paneles a visualizar

interface PanelConfigurable {
    void inicializar(Rango rango, CrearActions actions);
}

class PanelInicio extends JPanel implements PanelConfigurable {
    private PanelInicioButtons panelBtns;
    private JTextField tipoDeRango;

    public PanelInicio() {
        super(new FlowLayout(FlowLayout.CENTER, 10, 100));
        setBackground(Color.LIGHT_GRAY);
        this.add(new JLabel("Ingresa tu rol (Organizador/Usuario):"));

        tipoDeRango = new JTextField(15);
        this.add(tipoDeRango);

        panelBtns = new PanelInicioButtons();
        this.add(panelBtns);
    }

    public JTextField getTipoDeRango() {
        return tipoDeRango;
    }

    @Override
    public void inicializar(Rango rango, CrearActions actions) {
        panelBtns.setNavegadorActions(actions);
        this.revalidate();
        this.repaint();
    }
}

class PanelUsuario extends JPanel implements PanelConfigurable {
    private PanelUsuarioButtons panelBtns;

    public PanelUsuario() {
        super(new BorderLayout());
        setBackground(new Color(200, 200, 255));

        panelBtns = new PanelUsuarioButtons();
        this.add(panelBtns, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(Rango rango, CrearActions actions) {
        panelBtns.setNavegadorActions(actions);
        this.revalidate();
        this.repaint();
    }
}

class PanelOrganizador extends JPanel implements PanelConfigurable {
    private PanelOrganizadorButtons panelBtns;

    public PanelOrganizador() {
        super(new BorderLayout());
        setBackground(new Color(200, 255, 200));

        panelBtns = new PanelOrganizadorButtons();
        this.add(panelBtns, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(Rango rango, CrearActions actions) {
        panelBtns.setNavegadorActions(actions);
        this.revalidate();
        this.repaint();
    }
}

class PanelCrearTorneo extends JPanel implements PanelConfigurable {
    private PanelCrearTorneoButtons panelBtns;

    public PanelCrearTorneo() {
        super(new BorderLayout());
        setBackground(new Color(255, 255, 200));

        panelBtns = new PanelCrearTorneoButtons();
        this.add(panelBtns, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Panel Crear Torneo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        this.add(titleLabel, BorderLayout.NORTH);
    }

    @Override
    public void inicializar(Rango rango, CrearActions actions) {
        panelBtns.setNavegadorActions(actions);
        this.revalidate();
        this.repaint();
    }
}

// Panel Principal

class PanelPrincipal extends JPanel {
    private JPanel panelActual;
    private HashMap<String, JPanel> contenedorPaneles;

    public PanelPrincipal() {
        super(new BorderLayout());
        contenedorPaneles = new HashMap<>();

        contenedorPaneles.put("INICIO", new PanelInicio());
        contenedorPaneles.put("ORGANIZADOR", new PanelOrganizador());
        contenedorPaneles.put("USUARIO", new PanelUsuario());
        contenedorPaneles.put("CREAR_TORNEO", new PanelCrearTorneo());
    }

    public JPanel getPanel(String panelName) {
        return contenedorPaneles.get(panelName);
    }

    public <T extends JPanel & PanelConfigurable> T getPanel(String panelName, Class<T> type) {
        JPanel panel = contenedorPaneles.get(panelName);
        if (type.isInstance(panel)) {
            return type.cast(panel);
        }
        else {
            return null;
        }
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

// Navegador para manejar el historial de acciones del Usuario/Organizador

class Navegador {
    private PanelPrincipal panelPrincipal;

    // Historial de navegacion del Usuario/Organizador
    private Stack<ActionListener> historial;

    public Navegador(PanelPrincipal panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
        this.historial = new Stack<>();
    }

    public void mostrarPanelInicio() {
        // El historial se limpia por completo si se vuelve al inicio
        historial.clear();

        PanelInicio panelInicio = panelPrincipal.getPanel("INICIO", PanelInicio.class);

        // Paquete de acciones para PanelInicio
        InicioActions inicioActions = new InicioActions(this, panelInicio);

        panelInicio.inicializar(null, inicioActions);
        panelPrincipal.cambiarPanel(panelInicio);

        panelInicio.getTipoDeRango().setText("");
    }

    public void mostrarPanelUsuario(Rango rango) {
        historial.push(e -> mostrarPanelInicio());
        PanelUsuario panelUsuario = panelPrincipal.getPanel("USUARIO", PanelUsuario.class);

        // Paquete de acciones para Usuario
        UsuarioActions usuarioActions = new UsuarioActions(this);

        // Inicializar
        panelUsuario.inicializar(rango, usuarioActions);
        panelPrincipal.cambiarPanel(panelUsuario);
    }

    public void mostrarPanelOrganizador(Rango rango) {
        historial.push(e -> mostrarPanelInicio());
        PanelOrganizador panelOrganizador = panelPrincipal.getPanel("ORGANIZADOR", PanelOrganizador.class);

        // Paquete de acciones para Organizador
        OrganizadorActions organizadorActions = new OrganizadorActions(this, rango);

        // Inicializar
        panelOrganizador.inicializar(rango, organizadorActions);
        panelPrincipal.cambiarPanel(panelOrganizador);
    }

    public void mostrarPanelCrearTorneo(Rango rango) {
        historial.push(e -> mostrarPanelOrganizador(rango));
        PanelCrearTorneo panelCrearTorneo = panelPrincipal.getPanel("CREAR_TORNEO", PanelCrearTorneo.class);

        // Paquete de acciones para Organizador en CrearTorneo
        CrearTorneoActions crearTorneoActions = new CrearTorneoActions(this, rango);

        // Inicializar
        panelCrearTorneo.inicializar(rango, crearTorneoActions);
        panelPrincipal.cambiarPanel(panelCrearTorneo);
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