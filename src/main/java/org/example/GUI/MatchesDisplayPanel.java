package org.example.GUI;

import org.example.CodigoLogico.Participante;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MatchesDisplayPanel extends JPanel {
    private CuadrantePanel cuadrante1Panel;
    private CuadrantePanel cuadrante2Panel;
    private JLabel finalMatchLabel;
    private MatchDisplayPanel finalMatchPanel;
    private int numParticipantes;
    private boolean bracketGenerated = false;
    private final Dimension BASE_PREFERRED_SIZE = new Dimension(700, 500);

    public MatchesDisplayPanel() {
        super(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.BLACK);
        setPreferredSize(BASE_PREFERRED_SIZE);

        cuadrante1Panel = new CuadrantePanel("Cuadrante 1");
        cuadrante2Panel = new CuadrantePanel("Cuadrante 2");
        finalMatchLabel = new JLabel("FINAL", SwingConstants.CENTER);
        finalMatchLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        finalMatchLabel.setForeground(Color.WHITE);
        finalMatchPanel = new MatchDisplayPanel(null, null);

        this.numParticipantes = 0;
        updateDisplay();
    }

    public void setParticipantes(List<Participante> participantes) {
        this.bracketGenerated = true;
        this.numParticipantes = participantes.size();

        List<Participante> pList = (participantes != null) ? participantes : new ArrayList<>();

        if (pList.size() == 2) {
            cuadrante1Panel.setMatches(new ArrayList<>());
            cuadrante2Panel.setMatches(new ArrayList<>());
            finalMatchPanel.setParticipantes(pList.get(0), pList.get(1));
        } else if (pList.size() > 2) {
            int mid = pList.size() / 2;
            List<Participante> q1Participants = new ArrayList<>();
            List<Participante> q2Participants = new ArrayList<>();

            for (int i = 0; i < pList.size(); i += 2) {
                if (i < mid) {
                    q1Participants.add(pList.get(i));
                    q1Participants.add(pList.get(i + 1));
                }
                else {
                    q2Participants.add(pList.get(i));
                    q2Participants.add(pList.get(i + 1));
                }
            }

            cuadrante1Panel.setMatches(q1Participants);
            cuadrante2Panel.setMatches(q2Participants);
            finalMatchPanel.setParticipantes(null, null);
        }
        else {
            cuadrante1Panel.setMatches(new ArrayList<>());
            cuadrante2Panel.setMatches(new ArrayList<>());
            finalMatchPanel.setParticipantes(null, null);
        }
        updateDisplay();
    }

    public void setNumParticipantes(int num) {
        this.numParticipantes = num;
        this.bracketGenerated = false;
        cuadrante1Panel.setMatches(new ArrayList<>());
        cuadrante2Panel.setMatches(new ArrayList<>());
        finalMatchPanel.setParticipantes(null, null);
        updateDisplay();
    }

    public void setBracketGenerado(boolean generated) {
        this.bracketGenerated = generated;
        updateDisplay();
    }

    private void updateDisplay() {
        removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        if (numParticipantes == 0) {
            JLabel msg = new JLabel("Seleccione un torneo para ver los emparejamientos.", SwingConstants.CENTER);
            msg.setForeground(Color.WHITE);
            msg.setFont(new Font("SansSerif", Font.PLAIN, 18));
            add(msg, gbc);
        } else if ((numParticipantes & (numParticipantes - 1)) != 0) {
            JLabel msg = new JLabel("La cantidad de participantes debe ser una potencia de 2.", SwingConstants.CENTER);
            msg.setForeground(Color.RED);
            msg.setFont(new Font("SansSerif", Font.BOLD, 18));
            add(msg, gbc);
        } else if (!bracketGenerated) {
            JLabel msg = new JLabel("Haga clic en 'Generar Bracket Aleatorio' para ver los emparejamientos.", SwingConstants.CENTER);
            msg.setForeground(Color.GRAY);
            msg.setFont(new Font("SansSerif", Font.PLAIN, 16));
            add(msg, gbc);
        } else {
            if (numParticipantes == 2) {
                gbc.gridy = 0;
                gbc.gridx = 0;
                add(finalMatchLabel, gbc);
                gbc.gridy = 1;
                add(finalMatchPanel, gbc);
            } else {
                gbc.gridy = 0;
                gbc.gridx = 0;
                add(cuadrante1Panel, gbc);

                gbc.gridx = 1;
                add(cuadrante2Panel, gbc);
            }
        }
        revalidate();
        repaint();
    }
}
