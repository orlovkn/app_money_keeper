package com.ehoteam.model;

import com.ehoteam.saveload.SaveData;

import java.util.HashMap;
import java.util.List;

public class Statistics extends Common {

    // суммируем баланс на всех счетах с одинаковой валютой
    public static double getBalanceCurrency(Currency currency) {
        SaveData sd = SaveData.getInstance ();
        double amount = 0;
        for (Account account : sd.getAccounts ()) {
            if (currency.equals (account.getCurrency ())) amount += account.getAmount ();
        }
        return amount;
    }

    // суммируем с разными валютами
    public static double getBalance(Currency currency) {
        SaveData sd = SaveData.getInstance ();
        double amount = 0;
        for (Account account : sd.getAccounts ()) {
            amount += account.getAmount () * account.getCurrency ().getRateByCurrency (currency);
        }
        return amount;
    }

    // информация по доходным статьям
    public static HashMap<String, Double> getDataForChartOnIncomeArticles() {
        return getDataForChartOnArticle (true);
    }

    // информация по расходным статьям
    public static HashMap<String, Double> getDataForChartOnExpArticles() {
        return getDataForChartOnArticle (false);
    }

    // данные, необходимые для построения гистограм
    // income доход или расход
    private static HashMap<String, Double> getDataForChartOnArticle(boolean income) {
        List<Transaction> transactions = SaveData.getInstance ().getTransactions ();
        HashMap<String, Double> data = new HashMap<> ();

        for (Transaction t : transactions) {
            if ((income && t.getAmount () > 0) || (!income && t.getAmount () < 0)) {
                String key = t.getArticle ().getTitle ();
                double summa = 0; // значение по определённой категории
                double amount = t.getAmount ();

                if (!income) amount *= -1; // если это категория расхода, умножаем на -1, чтобы получить положительное значение

                if (data.containsKey (key)) summa = data.get (key);

                // подсчитываем сумму
                summa += amount * t.getAccount ().getCurrency ().getRateByCurrency (SaveData.getInstance ().getBaseCurrency ());
                data.put(key, round(summa));
            }
        }

        return data;
    }

    // округление
    private static double round(double value) {
        return (double) Math.round (value * 100) / 100;
    }
}
