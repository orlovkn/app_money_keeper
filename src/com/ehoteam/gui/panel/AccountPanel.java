package com.ehoteam.gui.panel;

import com.ehoteam.gui.MainFrame;
import com.ehoteam.gui.dialog.AccountAddEditDialog;
import com.ehoteam.gui.handler.FunctionsHandler;
import com.ehoteam.gui.table.AccountTableData;
import com.ehoteam.gui.toolbar.FunctionsToolBar;
import com.ehoteam.settings.Style;

public class AccountPanel extends RightPanel {

    public AccountPanel(MainFrame frame) {
        super(frame, new AccountTableData (), "ACCOUNTS", Style.ICON_PANEL_ACCOUNTS, new FunctionsToolBar (new FunctionsHandler (frame, new AccountAddEditDialog (frame))));
    }

}
