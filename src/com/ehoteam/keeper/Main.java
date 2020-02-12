package com.ehoteam.keeper;

import com.ehoteam.gui.MainFrame;
import com.ehoteam.model.*;
import com.ehoteam.saveload.SaveData;
import com.ehoteam.settings.Settings;
import com.ehoteam.settings.Text;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {
        init ();
        MainFrame frame = new MainFrame ();
        frame.setVisible (true);

        SaveData sd = new SaveData ().getInstance ();
//        sd.updateCurrencies ();
//        sd.save ();
        System.out.println (sd);
//        testModel();
    }

    private static void init() {
        try {
            Text.init ();
            Settings.init ();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
            ge.registerFont (Font.createFont (Font.TRUETYPE_FONT, Settings.FONT_ROBOTO_LIGHT));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace ();
        }
    }

    private static void testModel() throws Exception {
        Currency c1 = new Currency ("Рубль", "RUB", 1, true, true);
        Currency c2 = new Currency ("Доллар", "USD", 73, true, false);

        Account ac1 = new Account ("Кэш", c1, 1300);
        Account ac2 = new Account ("Резерв", c2, 340);
        Account ac3 = new Account ("Доход", c1, 0);

        Article ar1 = new Article ("Продукты");
        Article ar2 = new Article ("Машинка");
        Article ar3 = new Article ("Развлечения");
        Article ar4 = new Article ("Студия");

        ArrayList<Currency> currencies = new ArrayList<> ();
        currencies.add(c1);
        currencies.add(c2);

        ArrayList<Account> accounts = new ArrayList<> ();
        accounts.add(ac1);
        accounts.add(ac2);
        accounts.add(ac3);

        ArrayList<Article> articles = new ArrayList<> ();
        articles.add(ar1);
        articles.add(ar2);
        articles.add(ar3);
        articles.add(ar4);

        ArrayList<Transaction> transactions = new ArrayList<> ();
        transactions.add(new Transaction (ac3, ar4, 4500));
        transactions.add(new Transaction (ac2, ar2, -500, "Бенз"));
        transactions.add(new Transaction (ac1, ar1, -347));
        transactions.add(new Transaction (ac3, ar4, 18700, new Date ((new Date()).getTime () - (long) 86400000 * 30)));

        for (int i = 0; i < 50; i++) {
            Article tempArticle;
            Account tempAccount;

            if (Math.random () < 0.5) tempArticle = ar1;
            else tempArticle = ar2;

            if (Math.random () < 0.5) tempAccount = ac2;
            else tempAccount = ac3;

            double tempAmount = Math.round (Math.random () * (-100));

            Date tempDate = new Date ((long) (new Date().getTime () - (long) 86400000 * 30 * Math.random ()));

            transactions.add(new Transaction (tempAccount, tempArticle, tempAmount, tempDate));
        }

        ArrayList<Transfer> transfers = new ArrayList<> ();
        transfers.add(new Transfer (ac3, ac1, 3000, 3000));
        transfers.add(new Transfer (ac3, ac1, 5400, 5400));
        transfers.add(new Transfer (ac3, ac2, 6500, 87));

        for (Account a : accounts) {
            a.setAmountFromTransactionsAndTransfers (transactions, transfers);
        }

        SaveData sd = SaveData.getInstance ();
        sd.setArticles (articles);
        sd.setAccounts (accounts);
        sd.setCurrencies (currencies);
        sd.setTransactions (transactions);
        sd.setTransfers (transfers);

        sd.save ();
//        sd.load();
        System.out.println (sd);
    }
}
