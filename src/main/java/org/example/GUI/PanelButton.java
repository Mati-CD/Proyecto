package org.example.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelButton extends JButton {
    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private Color defaultBackgroundColor;

    /**
     * Constructor para crear un PanelButton.
     * Configura el botón con el texto, la fuente y los manejadores de eventos de ratón para los cambios de color.
     *
     * @param label El texto que se mostrará en el botón.
     * @param font La fuente a usar para el texto del botón.
     */
    public PanelButton(String label, Font font) {
        super(label);
        setFont(font);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (hoverBackgroundColor != null) {
                    setBackground(hoverBackgroundColor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (defaultBackgroundColor != null) {
                    setBackground(defaultBackgroundColor);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (pressedBackgroundColor != null) {
                    setBackground(pressedBackgroundColor);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (contains(e.getPoint())) {
                    setBackground(hoverBackgroundColor);
                } else {
                    setBackground(defaultBackgroundColor);
                }
            }
        });
    }

    /**
     * Establece el tamaño preferido para el botón.
     *
     * @param preferredSize Las dimensiones preferidas del botón.
     */
    public void setButtonPreferredSize(Dimension preferredSize) {
        setPreferredSize(preferredSize);
    }

    /**
     * Configura los colores de fondo para los estados normal, de hover y el color de texto.
     * También permite definir un borde opcional. El color de presionado se calcula automáticamente.
     *
     * @param backgroundColor Color de fondo por defecto.
     * @param foregroundColor Color del texto.
     * @param hoverColor Color de fondo cuando el cursor está sobre el botón.
     * @param borderWidth El ancho del borde. Si es > 0, se dibujará un borde.
     */
    public void setButtonColor(Color backgroundColor, Color foregroundColor,
                               Color hoverColor, int borderWidth) {
        this.defaultBackgroundColor = backgroundColor;
        this.hoverBackgroundColor = hoverColor;
        this.pressedBackgroundColor = new Color(
                Math.max(backgroundColor.getRed() - 30, 0),
                Math.max(backgroundColor.getGreen() - 30, 0),
                Math.max(backgroundColor.getBlue() - 30, 0)
        );

        setBackground(backgroundColor);
        setForeground(foregroundColor);

        if (borderWidth > 0) {
            Color borderColor = new Color(
                    Math.min(hoverColor.getRed() + 20, 255),
                    Math.min(hoverColor.getGreen() + 20, 255),
                    Math.min(hoverColor.getBlue() + 20, 255)
            );
            Border lineBorder = BorderFactory.createLineBorder(borderColor, borderWidth);
            Border emptyBorder = BorderFactory.createEmptyBorder(5, 15, 5, 15);
            setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        } else {
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color fillColor;
        if (getModel().isPressed()) {
            fillColor = pressedBackgroundColor;
        } else if (getModel().isRollover()) {
            fillColor = hoverBackgroundColor;
        } else {
            fillColor = defaultBackgroundColor;
        }

        if (fillColor != null) {
            g2.setColor(fillColor);
        } else {
            g2.setColor(getBackground() != null ? getBackground() : Color.GRAY);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        setContentAreaFilled(false);
        super.paintComponent(g);
    }
}
