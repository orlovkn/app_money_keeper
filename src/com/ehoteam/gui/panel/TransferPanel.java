package com.ehoteam.gui.panel;

import com.ehoteam.gui.MainFrame;
import com.ehoteam.gui.table.TransferTableData;
import com.ehoteam.gui.toolbar.FunctionsToolBar;
import com.ehoteam.settings.Style;

import javax.swing.*;

public class TransferPanel extends RightPanel {

    public TransferPanel(MainFrame frame) {
        super(frame, new TransferTableData (), "TRANSFERS", Style.ICON_PANEL_TRANSFERS,
                new JPanel[] {new FunctionsToolBar ()/*, new FilterPanel (frame)*/});
    }

}
