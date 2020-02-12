package com.ehoteam.gui.panel;

import com.ehoteam.gui.MainFrame;
import com.ehoteam.gui.table.AccountTableData;
import com.ehoteam.gui.table.ArticleTableData;
import com.ehoteam.gui.toolbar.FunctionsToolBar;
import com.ehoteam.settings.Style;

public class ArticlePanel extends RightPanel {

    public ArticlePanel(MainFrame frame) {
        super(frame, new ArticleTableData (), "ARTICLES", Style.ICON_PANEL_ARTICLES, new FunctionsToolBar ());
    }

}
