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

    public void setButtonPreferredSize(Dimension preferredSize) {
        setPreferredSize(preferredSize);
    }

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

        // Permitir que el texto se dibuje bien
        setContentAreaFilled(false);
        super.paintComponent(g);
    }
}
