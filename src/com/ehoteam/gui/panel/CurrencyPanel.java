package com.ehoteam.gui.panel;

import com.ehoteam.gui.MainFrame;
import com.ehoteam.gui.table.CurrencyTableData;
import com.ehoteam.gui.toolbar.FunctionsToolBar;
import com.ehoteam.settings.Style;

public class CurrencyPanel extends RightPanel {

    public CurrencyPanel(MainFrame frame) {
        super(frame, new CurrencyTableData (), "CURRENCIES", Style.ICON_PANEL_CURRENCIES, new FunctionsToolBar ());
    }

}
