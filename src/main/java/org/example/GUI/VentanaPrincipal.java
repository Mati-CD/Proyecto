package org.example.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Clase principal de la interfaz gráfica de la aplicación.
 * Representa la ventana principal (frame) que contiene y gestiona el panel principal y la navegación entre vistas.
 *
 * Esta ventana inicializa y configura los elementos principales de la GUI, estableciendo el titulo, tamaño
 * y el comportamiento al cerrar
 *
 */
public class VentanaPrincipal extends JFrame {

    private PanelPrincipal panelPrincipal;
    private Navegador navegador;

    /**
     * Constructor de la clase que inicializa la ventana principal de la aplicación.
     * Configura el tamaño, título, comportamiento al cerrar, y panel de contenido inicial.
     */
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
