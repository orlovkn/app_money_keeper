package com.ehoteam.gui.menu;

import com.ehoteam.gui.Refresh;
import com.ehoteam.settings.HandlerCode;
import com.ehoteam.settings.Style;
import com.ehoteam.settings.Text;

import javax.swing.*;

public class TablePopupMenu extends JPopupMenu implements Refresh {

    public TablePopupMenu() {
        super();
        init();
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void init() {
        JMenuItem editItem = new JMenuItem(Text.get("EDIT"));
        JMenuItem deleteItem = new JMenuItem(Text.get("DELETE"));

        editItem.setActionCommand(HandlerCode.EDIT);
        deleteItem.setActionCommand(HandlerCode.DELETE);

        editItem.setIcon(Style.ICON_MENU_POPUP_EDIT);
        deleteItem.setIcon(Style.ICON_MENU_POPUP_DELETE);

        add(editItem);
        add(deleteItem);
    }

}

