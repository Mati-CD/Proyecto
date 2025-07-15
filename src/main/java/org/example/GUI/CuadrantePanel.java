package org.example.GUI;

import org.example.CodigoLogico.Participante;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CuadrantePanel extends JPanel {
    private List<MatchDisplayPanel> matchPanels;
    private JLabel titleLabel;

    public CuadrantePanel(String title) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createVerticalStrut(10));

        matchPanels = new ArrayList<>();
    }

    public void setMatches(List<Participante> participantesPorCuadrante) {
        removeAll();
        matchPanels.clear();

        add(titleLabel);
        add(Box.createVerticalStrut(10));

        if (participantesPorCuadrante != null && participantesPorCuadrante.size() % 2 == 0) {
            for (int i = 0; i < participantesPorCuadrante.size(); i += 2) {
                Participante p1 = participantesPorCuadrante.get(i);
                Participante p2 = participantesPorCuadrante.get(i + 1);
                MatchDisplayPanel matchPanel = new MatchDisplayPanel(p1, p2);
                matchPanels.add(matchPanel);
                add(matchPanel);
                if (i + 2 < participantesPorCuadrante.size()) {
                    add(Box.createVerticalStrut(10));
                }
            }
        } else {
            JLabel noMatchesLabel = new JLabel("No hay partidos en este cuadrante.", SwingConstants.CENTER);
            noMatchesLabel.setForeground(Color.GRAY);
            noMatchesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(noMatchesLabel);
        }
        revalidate();
        repaint();
    }
}