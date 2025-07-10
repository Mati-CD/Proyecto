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

// Paneles con el grupo de botones que seran asignados a un panel en especifico

class InicioButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton organizadorBtn;
    private PanelButton usuarioBtn;

    public InicioButtons() {
        setLayout(new BorderLayout());
        Font font = new Font("SansSerif", Font.BOLD, 18);
        organizadorBtn = new PanelButton("Organizador", font);
        usuarioBtn = new PanelButton("Usuario", font);

        add(organizadorBtn, BorderLayout.WEST);
        add(usuarioBtn, BorderLayout.EAST);
    }

    @Override
    public void setButtonActions(AsignarAction asignarAction) {
        organizadorBtn.addActionListener(asignarAction.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
        usuarioBtn.addActionListener(asignarAction.getAction(ActionGUI.IR_A_USUARIO.getID()));
    }
}

class UsuarioButtons extends JPanel implements PanelButtonsGroup{
    private PanelButton irAInicioBtn;
    private PanelButton verEstadoActualTorneoBtn;
    private PanelButton verProxEncuentrosBtn;
    private PanelButton verEstadisticasBtn;

    public UsuarioButtons() {
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
    public void setButtonActions(AsignarAction asignarAction) {
        irAInicioBtn.addActionListener(asignarAction.getAction(ActionGUI.IR_A_INICIO.getID()));
    }
}

class OrganizadorButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton irAInicioBtn;
    private PanelButton crearTorneoBtn;
    private PanelButton inscribirParticipantesBtn;
    private PanelButton iniciarTorneoBtn;
    private PanelButton actualizarRegistroDeResultadosBtn;

    public OrganizadorButtons() {
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
    public void setButtonActions(AsignarAction asignarAction) {
        irAInicioBtn.addActionListener(asignarAction.getAction(ActionGUI.IR_A_INICIO.getID()));
        crearTorneoBtn.addActionListener(asignarAction.getAction(ActionGUI.CREAR_TORNEO.getID()));
        inscribirParticipantesBtn.addActionListener(asignarAction.getAction(ActionGUI.INSCRIBIR_PARTICIPANTES.getID()));
    }
}

// Clase temporal para el prototipo
class CrearTorneoButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton irAtrasBtn;

    public CrearTorneoButtons() {
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
    public void setButtonActions(AsignarAction asignarAction) {
        irAtrasBtn.addActionListener(asignarAction.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
    }
}

class InscribirParticipantesButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton irAtrasBtn;

    public InscribirParticipantesButtons() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(6, 153, 153));

        Font font = new Font("SansSerif", Font.BOLD, 18);

        // Crear Botones
        irAtrasBtn = new PanelButton("Volver atrás", font);

        irAtrasBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(irAtrasBtn);
    }

    @Override
    public void setButtonActions(AsignarAction asignarAction) {
        irAtrasBtn.addActionListener(asignarAction.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
    }
}

// Configurar paneles a visualizar

class PanelInicio extends JPanel implements PanelConfigurable {
    private InicioButtons buttonsGroup;

    public PanelInicio() {
        super(new FlowLayout(FlowLayout.CENTER, 10, 100));
        setBackground(Color.LIGHT_GRAY);
        buttonsGroup = new InicioButtons();
        this.add(buttonsGroup);
    }

    @Override
    public void inicializar(AsignarAction asignarAction) {
        buttonsGroup.setButtonActions(asignarAction);
        this.revalidate();
        this.repaint();
    }
}

class PanelUsuario extends JPanel implements PanelConfigurable {
    private UsuarioButtons buttonsGroup;

    public PanelUsuario() {
        super(new BorderLayout());
        setBackground(new Color(200, 200, 255));

        buttonsGroup = new UsuarioButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(AsignarAction asignarAction) {
        buttonsGroup.setButtonActions(asignarAction);
        this.revalidate();
        this.repaint();
    }
}

class PanelOrganizador extends JPanel implements PanelConfigurable {
    private OrganizadorButtons buttonsGroup;

    public PanelOrganizador() {
        super(new BorderLayout());
        setBackground(new Color(200, 255, 200));

        buttonsGroup = new OrganizadorButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);
    }

    @Override
    public void inicializar(AsignarAction asignarAction) {
        buttonsGroup.setButtonActions(asignarAction);
        this.revalidate();
        this.repaint();
    }
}

class PanelCrearTorneo extends JPanel implements PanelConfigurable {
    private CrearTorneoButtons buttonsGroup;

    public PanelCrearTorneo() {
        super(new BorderLayout());
        setBackground(new Color(255, 255, 200));

        buttonsGroup = new CrearTorneoButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Panel Crear Torneo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        this.add(titleLabel, BorderLayout.NORTH);
    }

    @Override
    public void inicializar(AsignarAction asignarAction) {
        buttonsGroup.setButtonActions(asignarAction);
        this.revalidate();
        this.repaint();
    }
}

class PanelInscribirParticipantes extends JPanel implements PanelConfigurable {
    private InscribirParticipantesButtons buttonsGroup;

    public PanelInscribirParticipantes() {
        super(new BorderLayout());
        setBackground(new Color(6, 153, 153));

        buttonsGroup = new InscribirParticipantesButtons();
        this.add(buttonsGroup, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Panel Inscribir Participantes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        this.add(titleLabel, BorderLayout.NORTH);
    }

    @Override
    public void inicializar(AsignarAction asignarAction) {
        buttonsGroup.setButtonActions(asignarAction);
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
        contenedorPaneles.put("INSCRIBIR_PARTICIPANTES", new PanelInscribirParticipantes());
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
    private Stack<ActionListener> historial;

    // asignarAction es un objeto que implementa AsignarAction
    private AsignarAction asignarAction;

    public Navegador(PanelPrincipal panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
        this.historial = new Stack<>();
        this.asignarAction = new AbstractAsignarAction(this) {};
    }

    public void mostrarPanelInicio() {
        // El historial se limpia por completo si se vuelve al inicio
        historial.clear();

        PanelInicio panelInicio = panelPrincipal.getPanel("INICIO", PanelInicio.class);

        panelInicio.inicializar(asignarAction);
        panelPrincipal.cambiarPanel(panelInicio);
    }

    public void mostrarPanelUsuario() {
        historial.push(e -> mostrarPanelInicio());
        PanelUsuario panelUsuario = panelPrincipal.getPanel("USUARIO", PanelUsuario.class);

        // Inicializar
        panelUsuario.inicializar(asignarAction);
        panelPrincipal.cambiarPanel(panelUsuario);
    }

    public void mostrarPanelOrganizador() {
        historial.push(e -> mostrarPanelInicio());
        PanelOrganizador panelOrganizador = panelPrincipal.getPanel("ORGANIZADOR", PanelOrganizador.class);

        // Inicializar
        panelOrganizador.inicializar(asignarAction);
        panelPrincipal.cambiarPanel(panelOrganizador);
    }

    public void mostrarPanelCrearTorneo() {
        historial.push(e -> mostrarPanelOrganizador());
        PanelCrearTorneo panelCrearTorneo = panelPrincipal.getPanel("CREAR_TORNEO", PanelCrearTorneo.class);

        // Inicializar
        panelCrearTorneo.inicializar(asignarAction);
        panelPrincipal.cambiarPanel(panelCrearTorneo);
    }

    public void mostrarPanelInscribirParticipantes() {
        historial.push(e -> mostrarPanelOrganizador());
        PanelInscribirParticipantes panelInscribirParticipantes = panelPrincipal.getPanel("INSCRIBIR_PARTICIPANTES", PanelInscribirParticipantes.class);

        // Inicializar
        panelInscribirParticipantes.inicializar(asignarAction);
        panelPrincipal.cambiarPanel(panelInscribirParticipantes);

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