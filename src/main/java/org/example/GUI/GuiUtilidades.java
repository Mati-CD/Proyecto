package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class GuiUtilidades {
    public static void showMessageOnce(Component parentComponent, String message, String title, int messageType) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
        });
    }
}
