package org.example.GUI;

import org.example.CodigoLogico.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MatchesDisplayPanel extends JPanel {
    private CuadrantePanel cuadrante1Panel;
    private CuadrantePanel cuadrante2Panel;
    private JLabel finalMatchLabel;
    private MatchDisplayPanel finalMatchPanel;

    private JPanel cardPanel;
    private JPanel emptyMessagePanel;
    private JPanel generateBracketMessagePanel;
    private JPanel twoParticipantsPanel;
    private JPanel multipleParticipantsPanel;

    private int numParticipantes = 0;
    private boolean bracketGenerated = false;

    public MatchesDisplayPanel() {
        super(new BorderLayout());
        setOpaque(true);
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        cuadrante1Panel = new CuadrantePanel("Cuadrante 1");
        cuadrante1Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        cuadrante2Panel = new CuadrantePanel("Cuadrante 2");
        cuadrante2Panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        finalMatchLabel = new JLabel("FINAL", SwingConstants.CENTER);
        finalMatchLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        finalMatchLabel.setForeground(Color.WHITE);
        finalMatchPanel = new MatchDisplayPanel(null, null);
        finalMatchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        cardPanel = new JPanel(new CardLayout());
        cardPanel.setOpaque(false);

        emptyMessagePanel = new JPanel(new GridBagLayout());
        emptyMessagePanel.setOpaque(false);
        JLabel msg0 = new JLabel("Seleccione un torneo para ver los emparejamientos.", SwingConstants.CENTER);
        msg0.setForeground(Color.WHITE);
        msg0.setFont(new Font("SansSerif", Font.PLAIN, 18));
        emptyMessagePanel.add(msg0);
        cardPanel.add(emptyMessagePanel, "EMPTY_MESSAGE");

        generateBracketMessagePanel = new JPanel(new GridBagLayout());
        generateBracketMessagePanel.setOpaque(false);
        JLabel msgGenerate = new JLabel("Haga clic en 'Generar Bracket Aleatorio' para ver los emparejamientos.", SwingConstants.CENTER);
        msgGenerate.setForeground(Color.GRAY);
        msgGenerate.setFont(new Font("SansSerif", Font.PLAIN, 16));
        generateBracketMessagePanel.add(msgGenerate);
        cardPanel.add(generateBracketMessagePanel, "GENERATE_BRACKET_MESSAGE");

        twoParticipantsPanel = new JPanel();
        twoParticipantsPanel.setOpaque(false);
        twoParticipantsPanel.setLayout(new BoxLayout(twoParticipantsPanel, BoxLayout.Y_AXIS));
        twoParticipantsPanel.add(Box.createVerticalGlue());
        finalMatchLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        twoParticipantsPanel.add(finalMatchLabel);
        twoParticipantsPanel.add(Box.createVerticalStrut(10));
        finalMatchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        twoParticipantsPanel.add(finalMatchPanel);
        twoParticipantsPanel.add(Box.createVerticalGlue());
        cardPanel.add(twoParticipantsPanel, "TWO_PARTICIPANTS");

        multipleParticipantsPanel = new JPanel();
        multipleParticipantsPanel.setOpaque(false);
        multipleParticipantsPanel.setLayout(new BoxLayout(multipleParticipantsPanel, BoxLayout.X_AXIS));

        JPanel quadrantsWrapper = new JPanel();
        quadrantsWrapper.setOpaque(false);
        quadrantsWrapper.setLayout(new BoxLayout(quadrantsWrapper, BoxLayout.X_AXIS));

        cuadrante1Panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        quadrantsWrapper.add(cuadrante1Panel);

        quadrantsWrapper.add(Box.createRigidArea(new Dimension(10, 0)));

        cuadrante2Panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        quadrantsWrapper.add(cuadrante2Panel);

        multipleParticipantsPanel.add(Box.createHorizontalGlue());
        multipleParticipantsPanel.add(quadrantsWrapper);
        multipleParticipantsPanel.add(Box.createHorizontalGlue());

        cardPanel.add(multipleParticipantsPanel, "MULTIPLE_PARTICIPANTS");

        add(cardPanel, BorderLayout.CENTER);

        updateDisplay();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(700, 500);
    }

    public void mostrarBracketSorteado(List<Participante> participantes) {
        this.bracketGenerated = true;
        this.numParticipantes = participantes != null ? participantes.size() : 0;

        if (numParticipantes < 2) {
            cuadrante1Panel.setMatches(new ArrayList<>());
            cuadrante2Panel.setMatches(new ArrayList<>());
            finalMatchPanel.setParticipantes(null, null);
            showCard("EMPTY_MESSAGE");
        } else if (numParticipantes == 2) {
            finalMatchPanel.setParticipantes(participantes.get(0), participantes.get(1));
            cuadrante1Panel.setMatches(new ArrayList<>());
            cuadrante2Panel.setMatches(new ArrayList<>());
            showCard("TWO_PARTICIPANTS");
        }
        else {
            int mitad = numParticipantes / 2;
            List<Participante> q1 = participantes.subList(0, mitad);
            List<Participante> q2 = participantes.subList(mitad, numParticipantes);

            cuadrante1Panel.setMatches(q1);
            cuadrante2Panel.setMatches(q2);
            finalMatchPanel.setParticipantes(null, null);
            showCard("MULTIPLE_PARTICIPANTS");
        }

        revalidate();
        repaint();
    }

    public void setNumParticipantes(int numParticipantes) {
        this.numParticipantes = numParticipantes;
        updateDisplay();
    }

    private void showCard(String cardName) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, cardName);
    }

    public void setBracketGenerado(boolean generated) {
        this.bracketGenerated = generated;
        updateDisplay();
    }

    private void updateDisplay() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();

        if (numParticipantes == 0) {
            cl.show(cardPanel, "EMPTY_MESSAGE");
        } else if (!bracketGenerated) {
            cl.show(cardPanel, "GENERATE_BRACKET_MESSAGE");
        } else if (numParticipantes == 2) {
            cl.show(cardPanel, "TWO_PARTICIPANTS");
        }
        else {
            cl.show(cardPanel, "MULTIPLE_PARTICIPANTS");
        }

        revalidate();
        repaint();
    }
}

