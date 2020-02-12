package com.ehoteam.gui;

import com.ehoteam.gui.menu.MainMenu;
import com.ehoteam.gui.panel.*;
import com.ehoteam.gui.toolbar.MainToolBar;
import com.ehoteam.settings.Style;
import com.ehoteam.settings.Text;

import javax.swing.*;
import java.awt.*;

public final class MainFrame extends JFrame implements Refresh{

    private GridBagConstraints constraints;
    private final MainMenu mb;
    private final LeftPanel leftPanel;
    private RightPanel rightPanel;
    private final MainToolBar tb;

    public MainFrame() {
        super (Text.get ("PROGRAM_NAME"));
        setResizable (false);
        setIconImage (Style.ICON_MAIN.getImage ());

        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

//        ConfirmDialog.show (this, "да или нет", "Заголовок");
//        new AboutDialog ().setVisible (true);

//        new AccountAddEditDialog (this).showDialog ();
//        new ArticleAddEditDialog (this).showDialog ();
//        CurrencyAddEditDialog temp = new CurrencyAddEditDialog(this);
//        temp.setCommon (SaveData.getInstance ().getBaseCurrency ());
//        temp.showDialog ();
        //new CurrencyAddEditDialog (this).showDialog ();
        /*try {
            ArticleAddEditDialog temp = new ArticleAddEditDialog(this);
            temp.setCommon (new Article ("текст"));
            temp.showDialog ();
        } catch (Exception e) {

        }*/



        mb = new MainMenu (this);
        setJMenuBar (mb);

        setLayout (new GridBagLayout ());

        constraints = new GridBagConstraints ();

        // ширина окна
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;

        tb = new MainToolBar ();
        add(tb, constraints);

        // toolbar
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.NORTH;

        leftPanel = new LeftPanel (this);
        add (leftPanel, constraints);

        setRightPanel(new TransactionPanel (this));
//        add (new FunctionsToolBar (), constraints);
        // leftpanel

//        add (new MainDatePicker ().getDataPicker (), constraints);

        pack ();
        setLocationRelativeTo (null);
    }

    @Override
    public void refresh() {
        SwingUtilities.updateComponentTreeUI (this);
        mb.refresh ();
        leftPanel.refresh ();
//        rightPanel.refresh ();
        pack ();
    }

    public MainMenu getMenu() {
        return mb;
    }

    private void setRightPanel(RightPanel panel) {
        if (rightPanel != null) remove(rightPanel);
        constraints.gridy = 1;
        constraints.gridx = 1;
        rightPanel = panel;
        panel.setBorder(Style.BORDER_PANEL);
        add(rightPanel, constraints);
        pack();
    }

    public RightPanel getRightPanel() {
        return rightPanel;
    }
}
