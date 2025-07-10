package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class InicioButtons extends JPanel implements PanelButtonsGroup {
    private PanelButton organizadorBtn;
    private PanelButton usuarioBtn;

    public InicioButtons() {
        setLayout(new BorderLayout());
        Font font = new Font("SansSerif", Font.BOLD, 18);
        organizadorBtn = new PanelButton("Organizador", font);
        usuarioBtn = new PanelButton("Usuario", font);

        add(organizadorBtn, BorderLayout.WEST);
        add(usuarioBtn, BorderLayout.EAST);
    }

    @Override
    public void setButtonActions(ActionAssigner actionAssigner) {
        organizadorBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_ORGANIZADOR.getID()));
        usuarioBtn.addActionListener(actionAssigner.getAction(ActionGUI.IR_A_USUARIO.getID()));
    }
}
