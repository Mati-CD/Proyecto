package org.example.GUI;

import org.example.CodigoLogico.Participante;

import javax.swing.*;
import java.awt.*;

/**
 * Panel gráfico que muestra un enfrentamiento entre dos participantes.
 */
public class MatchDisplayPanel extends JPanel {
    private Participante p1;
    private Participante p2;

    private final Color BOX_BORDER_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = Color.WHITE;
    private final Font FONT = new Font("SansSerif", Font.PLAIN, 20);
    private final int BOX_WIDTH = 250;
    private final int BOX_HEIGHT = 28;
    private final int PADDING_Y = 5;

    /**
     * Crea un panel que muestra un enfrentamiento entre dos participantes.
     *
     * @param p1 Primer participante (puede ser null)
     * @param p2 Segundo participante (puede ser null)
     */
    public MatchDisplayPanel(Participante p1, Participante p2) {
        this.p1 = p1;
        this.p2 = p2;
        setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT + PADDING_Y * 2));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(PADDING_Y, 0, PADDING_Y, 0));
    }

    /**
     * Establece los participantes que se mostrarán en el panel.
     *
     * @param p1 Primer participante (puede ser null)
     * @param p2 Segundo participante (puede ser null)
     */
    public void setParticipantes(Participante p1, Participante p2) {
        this.p1 = p1;
        this.p2 = p2;
        repaint();
    }

    /**
     * Dibuja el contenido del panel con los nombres de los participantes.
     *
     * @param g Objeto Graphics usado para pintar el componente
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(BOX_BORDER_COLOR);
        g2d.setFont(FONT);

        g2d.drawRect(0, PADDING_Y, BOX_WIDTH - 1, BOX_HEIGHT);

        String p1Name = (p1 != null) ? p1.getNombre() : "Pendiente";
        String p2Name = (p2 != null) ? p2.getNombre() : "Pendiente";
        String matchText = p1Name + " vs " + p2Name;

        g2d.setColor(TEXT_COLOR);
        drawCenteredString(g2d, matchText, new Rectangle(0, PADDING_Y, BOX_WIDTH, BOX_HEIGHT), FONT);
    }

    /**
     * Dibuja un texto centrado dentro de un rectángulo.
     *
     * @param g     Objeto Graphics usado para dibujar
     * @param text  Texto a dibujar
     * @param rect  Área donde centrar el texto
     * @param font  Fuente del texto
     */
    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(text, x, y);
    }
}
