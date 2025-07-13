package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelButton extends JButton {
    public PanelButton(String label, Font font) {
        super(label);
        this.setFont(font);
    }
    public void setButtonPreferredSize(Dimension preferredSize) {
        setPreferredSize(preferredSize);
    }
}
