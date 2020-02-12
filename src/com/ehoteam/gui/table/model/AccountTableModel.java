package com.ehoteam.gui.table.model;

import com.ehoteam.model.Account;
import com.ehoteam.saveload.SaveData;
import com.ehoteam.settings.Format;

public class AccountTableModel extends MainTableModel {

    private static final int TITLE = 0;
    private static final int AMOUNT = 1;

    public AccountTableModel(String[] columns) {
        super (SaveData.getInstance ().getAccounts (), columns);
    }

    @Override
    protected void updateData() {
        data = SaveData.getInstance ().getAccounts ();
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (data.isEmpty()) return null;
        Account account = (Account) data.get(row);
        switch (column) {
            case TITLE:
                return account.getTitle ();
            case AMOUNT:
                return Format.amount (account.getAmount (), account.getCurrency ());
        }
        return null;
    }
}
