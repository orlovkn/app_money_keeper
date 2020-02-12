package com.ehoteam.gui.table;

import com.ehoteam.gui.table.model.AccountTableModel;
import com.ehoteam.settings.Style;

import javax.swing.*;

public class AccountTableData extends TableData {

    private static final String[] columns = new String[]{"TITLE", "AMOUNT"};
    private static final ImageIcon[] icons = new ImageIcon[]{Style.ICON_TITLE, Style.ICON_AMOUNT};

    public AccountTableData() {
        super (new AccountTableModel (columns), columns, icons);
    }
}
