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