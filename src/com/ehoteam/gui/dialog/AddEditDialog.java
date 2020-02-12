package com.ehoteam.gui.dialog;

import com.ehoteam.exception.ModelException;
import com.ehoteam.gui.MainButton;
import com.ehoteam.gui.MainFrame;
import com.ehoteam.model.Common;
import com.ehoteam.settings.HandlerCode;
import com.ehoteam.settings.Style;
import com.ehoteam.settings.Text;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AddEditDialog extends JDialog {
    protected LinkedHashMap<String, JComponent> components = new LinkedHashMap();
    protected LinkedHashMap<String, ImageIcon> icons = new LinkedHashMap();
    protected LinkedHashMap<String, Object> values = new LinkedHashMap(); // значение текстового поля
    protected Common c;

    public AddEditDialog(MainFrame frame) {
        super (frame, Text.get ("ADD"), true);
        setResizable (false);
    }

    public Common getCommon() {
        return c;
    }

    public void setCommon(Common c) {
        this.c = c;
    }

    public final void showDialog() {
        setDialog ();
        setVisible (true);
    }

    public final void closeDialog() {
        setVisible (false);
        this.c = null;
        components.clear ();
        icons.clear ();
        values.clear ();
        dispose ();// очищение места в оперативной памяти
    }

    public boolean isAdd() {
        return c == null;
    }

    abstract public Common getCommonFromForm() throws ModelException;

    abstract protected void init();

    abstract protected void setValues();

    private void setDialog() {
        init ();
        if (isAdd ()) {
            setTitle (Text.get ("ADD"));
            setIconImage (Style.ICON_ADD.getImage ());
        } else {
            setValues ();
            setTitle (Text.get ("EDIT"));
            setIconImage (Style.ICON_EDIT.getImage ());
        }
        getContentPane ().removeAll (); // очистка полей
        // расположение панелей с вертикальным выравниванием
        getContentPane ().setLayout (new BoxLayout (getContentPane (), BoxLayout.Y_AXIS));
        ((JPanel) getContentPane ()).setBorder (Style.BORDER_DIALOG);

        for (Map.Entry<String, JComponent> entry : components.entrySet ()) {
            String key = entry.getKey ();
            JLabel label = new JLabel (Text.get (key));
            label.setIcon (icons.get (key));
            label.setFont (Style.FONT_DIALOG_LABEL);

            JComponent component = entry.getValue ();
            if (component instanceof JTextField) {
                component.setPreferredSize (Style.DIMENSION_DIALOG_TEXTFIELD_SIZE);
                if (values.containsKey (key))
                    ((JTextField) component).setText ("" + values.get (key)); // установка текста
            } else if (component instanceof JComboBox) {
                if (values.containsKey (key)) ((JComboBox) component).setSelectedItem (values.get (key));
            } else if (component instanceof JDatePickerImpl) {
//                if (values.containsKey(key)) ((UtilDateModel) ((JDatePickerImpl) component).getModel()).setValue((Date) values.get(key));
            }
            component.setAlignmentX (JComponent.LEFT_ALIGNMENT);
            add (label);
            add (Box.createVerticalStrut (Style.PADDING_DIALOG));
            add (component);
            add (Box.createVerticalStrut (Style.PADDING_DIALOG));
        }

        MainButton ok = new MainButton (Text.get ("ADD"), Style.ICON_OK, null, HandlerCode.ADD);
        if (!isAdd ()) {
            ok.setActionCommand (HandlerCode.EDIT);
            ok.setText (Text.get ("EDIT"));
        }
        MainButton cancel = new MainButton (Text.get ("CANCEL"), Style.ICON_CANCEL, null, HandlerCode.CANCEL);

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BorderLayout ());
        panelButtons.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        panelButtons.add(ok, BorderLayout.WEST);
        panelButtons.add(Box.createRigidArea(Style.DIMENSION_DIALOG_PADDING_BUTTON), BorderLayout.CENTER);
        panelButtons.add(cancel, BorderLayout.EAST);

        add(panelButtons);
        pack();
        setLocationRelativeTo(null);
    }

    protected class CommonComboBox extends JComboBox {

        public CommonComboBox(Object[] objs) {
            super(objs);
            setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    DefaultListCellRenderer renderer = (DefaultListCellRenderer) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    Common c = (Common) value;
                    if (c != null) renderer.setText(c.getValueForComboBox());
                    return renderer;
                }
            });
        }

    }
}
