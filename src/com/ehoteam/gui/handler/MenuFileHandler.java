package com.ehoteam.gui.handler;

import com.ehoteam.gui.MainFileChooser;
import com.ehoteam.gui.MainFrame;
import com.ehoteam.gui.dialog.ConfirmDialog;
import com.ehoteam.gui.dialog.ErrorDialog;
import com.ehoteam.saveload.SaveData;
import com.ehoteam.settings.HandlerCode;
import com.ehoteam.settings.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MenuFileHandler extends Handler{

    private final MainFileChooser fc;

    public MenuFileHandler(MainFrame frame) {
        super(frame);
        fc = new MainFileChooser(frame);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case HandlerCode.MENU_FILE_NEW: {
                Settings.setFileSave(null);
                SaveData.getInstance().clear();
                break;
            }
            case HandlerCode.MENU_FILE_OPEN: {
                int result = fc.open();
                if (result == JFileChooser.APPROVE_OPTION) {
                    Settings.setFileSave(fc.getSelectedFile());
                    SaveData.getInstance().clear();
                    SaveData.getInstance().load();
                }
                break;
            }
            case HandlerCode.MENU_FILE_SAVE: {
                if (Settings.getFileSave() == null) {
                    int result = fc.save();
                    if (result == JFileChooser.APPROVE_OPTION) {
                        String path = fc.getSelectedFile().getAbsolutePath();
                        String ext = path.substring(path.lastIndexOf(".") + 1);
                        if (ext.equals(Settings.SAVE_FILE_EXT)) Settings.setFileSave(new File(path));
                        else Settings.setFileSave(new File (path + "." + Settings.SAVE_FILE_EXT));
                    }
                }
                if (Settings.getFileSave() != null) SaveData.getInstance().save();
                break;
            }
            case HandlerCode.MENU_FILE_UPDATE_CURRENCIES: {
                try {
                    SaveData.getInstance().updateCurrencies();
                } catch (Exception ex) {
                    ErrorDialog.show(frame, "ERROR_UPDATE_CURRENCIES");
                }
                break;
            }
            case HandlerCode.MENU_FILE_EXIT: {
                if (SaveData.getInstance().isSaved()) System.exit(0);
                else {
                    int result = ConfirmDialog.show(frame, "CONFIRM_EXIT_TEXT", "CONFIRM_EXIT_TITLE");
                    if (result == JOptionPane.YES_OPTION) System.exit(0);
                }
            }

        }
        super.actionPerformed(ae);
    }

}
