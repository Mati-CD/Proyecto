package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
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
