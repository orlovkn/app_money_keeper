package com.ehoteam.gui.panel;

import com.ehoteam.gui.MainFrame;
import com.ehoteam.gui.table.TransactionTableData;
import com.ehoteam.gui.toolbar.FunctionsToolBar;
import com.ehoteam.settings.Style;

import javax.swing.*;

public class TransactionPanel extends RightPanel {

    public TransactionPanel(MainFrame frame) {
        super(frame, new TransactionTableData (), "TRANSACTIONS", Style.ICON_PANEL_TRANSACTIONS,
                new JPanel[] {new FunctionsToolBar ()/*, new FilterPanel (frame)*/});
    }

}
