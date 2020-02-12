package com.ehoteam.gui.dialog;

import com.ehoteam.gui.MainFrame;
import com.ehoteam.settings.Text;

import javax.swing.*;

public class ErrorDialog {

    public static void show(MainFrame frame, String text) {
        JOptionPane.showMessageDialog (frame, Text.get (text), Text.get("ERROR"), JOptionPane.ERROR_MESSAGE);
    }

}
