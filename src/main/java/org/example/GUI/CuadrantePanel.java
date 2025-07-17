package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel que representa un cuadrante de emparejamientos dentro del torneo.
 * Muestra los enfrentamientos entre participantes en forma vertical.
 */
public class CuadrantePanel extends JPanel {

    private String title;
    private List<MatchDisplayPanel> matchPanels;
    private JPanel matchesContainerPanel;

    private final Font titleFont = new Font("SansSerif", Font.BOLD, 18);

    /**
     * Crea un nuevo panel de cuadrante con un título.
     *
     * @param title Título que se muestra en el borde del panel
     */
    public CuadrantePanel(String title) {
        this.title = title;
        setOpaque(false);
        setLayout(new BorderLayout());

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                this.title,
                TitledBorder.CENTER,
                TitledBorder.TOP,
                titleFont,
                Color.WHITE
        );

        setBorder(BorderFactory.createCompoundBorder(
                titledBorder,
                BorderFactory.createEmptyBorder(20, 20, 20, 10)
        ));

        matchesContainerPanel = new JPanel();
        matchesContainerPanel.setOpaque(false);
        matchesContainerPanel.setLayout(new BoxLayout(matchesContainerPanel, BoxLayout.Y_AXIS));
        matchesContainerPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(matchesContainerPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        matchPanels = new ArrayList<>();
    }

    /**
     * Establece la lista de participantes para mostrar sus enfrentamientos.
     * Se crean pares consecutivos de participantes.
     *
     * @param participants Lista de participantes (mínimo 2 para crear enfrentamientos)
     */
    public void setMatches(List<Participante> participants) {
        matchesContainerPanel.removeAll();
        matchPanels.clear();

        if (participants != null && participants.size() >= 2) {
            for (int i = 0; i < participants.size(); i += 2) {
                Participante p1 = participants.get(i);
                Participante p2 = participants.get(i + 1);
                MatchDisplayPanel matchPanel = new MatchDisplayPanel(p1, p2);

                matchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                matchPanels.add(matchPanel);
                matchesContainerPanel.add(matchPanel);
                if (i + 2 < participants.size()) {
                    matchesContainerPanel.add(Box.createVerticalStrut(5));
                }
            }
        }

        matchesContainerPanel.add(Box.createVerticalGlue());
        revalidate();
        repaint();
    }
}