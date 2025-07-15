package org.example.GUI;

import org.example.CodigoLogico.Participante;

import javax.swing.*;
import java.awt.*;

public class MatchDisplayPanel extends JPanel {
    private Participante p1;
    private Participante p2;
    private final Color BOX_BORDER_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = Color.WHITE;
    private final Font FONT = new Font("SansSerif", Font.PLAIN, 14);
    private final int BOX_WIDTH = 250;
    private final int BOX_HEIGHT = 28;
    private final int PADDING_Y = 5;

    public MatchDisplayPanel(Participante p1, Participante p2) {
        this.p1 = p1;
        this.p2 = p2;
        setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT + PADDING_Y * 2));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(PADDING_Y, 0, PADDING_Y, 0));
    }

    public void setParticipantes(Participante p1, Participante p2) {
        this.p1 = p1;
        this.p2 = p2;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(BOX_BORDER_COLOR);
        g2d.setFont(FONT);

        g2d.drawRect(0, PADDING_Y, BOX_WIDTH -1, BOX_HEIGHT);

        String p1Name = (p1 != null) ? p1.getNombre() : "Pendiente";
        String p2Name = (p2 != null) ? p2.getNombre() : "Pendiente";
        String matchText = p1Name + " vs " + p2Name;

        g2d.setColor(TEXT_COLOR);
        drawCenteredString(g2d, matchText, new Rectangle(0, PADDING_Y, BOX_WIDTH, BOX_HEIGHT), FONT);
    }

    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(text, x, y);
    }
}
