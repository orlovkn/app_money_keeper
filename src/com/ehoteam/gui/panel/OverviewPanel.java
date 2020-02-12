package com.ehoteam.gui.panel;

import com.ehoteam.gui.MainFrame;
import com.ehoteam.gui.table.TableData;
import com.ehoteam.gui.table.TransactionTableData;
import com.ehoteam.settings.Settings;
import com.ehoteam.settings.Style;

import javax.swing.*;

public class OverviewPanel extends RightPanel {

    public OverviewPanel(MainFrame frame) {
        super(frame, new TransactionTableData (Settings.COUNT_OVERVIEW_ROWS), "LAST_TRANSACTIONS", Style.ICON_PANEL_OVERVIEW);
    }

}
