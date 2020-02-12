package com.ehoteam.saveload;

import com.ehoteam.exception.ModelException;
import com.ehoteam.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public final class SaveData {

    private static SaveData instance; // класс одиночка
    private List<Article> articles = new ArrayList<> ();
    private List<Account> accounts = new ArrayList<> ();
    private List<Currency> currencies = new ArrayList<> ();
    private List<Transaction> transactions = new ArrayList<> ();
    private List<Transfer> transfers = new ArrayList<> ();

    private final Filter filter;
    private Common oldCommon;
    private boolean saved = false;

    public SaveData() {
        load ();
        this.filter = new Filter ();
    }

    public void load() {
        SaveLoad.load (this);
        sort ();
        for(Account a : accounts) {
            a.setAmountFromTransactionsAndTransfers (transactions, transfers);
        }
    }

    public void clear() {
        articles.clear ();
        currencies.clear ();
        accounts.clear ();
        transfers.clear ();
        transactions.clear ();
    }

    private void sort() {
        // сортировка с игнорированием регистра
        this.articles.sort ((Article a, Article a1) -> a.getTitle ().compareToIgnoreCase (a1.getTitle ()));
        this.accounts.sort ((Account a, Account a1) -> a.getTitle ().compareToIgnoreCase (a1.getTitle ()));
        // сортировка по дате
        this.transactions.sort ((Transaction t, Transaction t1) -> t1.getDate ().compareTo (t.getDate ()));
        this.transfers.sort ((Transfer t, Transfer t1) -> t1.getDate ().compareTo (t.getDate ()));
        // сортировка валют
        this.currencies.sort (new Comparator<Currency> () {
            @Override
            public int compare(Currency c, Currency c1) {
                if (c.isBase ()) return -1; // если базовая, ставим всегда вверху
                if (c1.isBase ()) return 1;

                if (c.isOn () ^ c1.isOn ()) {
                    if (c.isOn ()) return 1;
                    else return -1;
                }
                return c.getTitle ().compareToIgnoreCase (c1.getTitle ());
            }
        });
    }

    public void save() {
        SaveLoad.save (this);
        saved = true;
    }

    public boolean isSaved() {
        return saved;
    }

    public static SaveData getInstance() {
        if (instance == null) instance = new SaveData ();
        return instance;
    }

    public Filter getFilter() {
        return filter;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        if (articles != null) this.articles = articles;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        if (accounts != null) this.accounts = accounts;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        if (currencies != null) this.currencies = currencies;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        if (transactions != null) this.transactions = transactions;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        if (transfers != null) this.transfers = transfers;
    }

    // возвращаем базовую валюту
    public Currency getBaseCurrency() {
        for (Currency c : currencies) {
            if (c.isBase ()) return c;
        }
        return new Currency ();
    }

    // спиок всех включенных валют
    public ArrayList<Currency> getEnableCurrencies() {
        ArrayList<Currency> list = new ArrayList<> ();
        for(Currency c : currencies)
            if(c.isOn ()) list.add (c);
        return list;
    }

    public List<Transaction> getFilterTransactions() {
        ArrayList<Transaction> list = new ArrayList<> ();
        for(Transaction t : transactions)
            if (filter.check (t.getDate ())) list.add (t);
        return list;
    }

    public List<Transfer> getFilterTransfers() {
        ArrayList<Transfer> list = new ArrayList<> ();
        for(Transfer t : transfers)
            if (filter.check (t.getDate ())) list.add (t);
        return list;
    }

    // вывод последних элементов на морду
    public List<Transaction> getFilterTransactionsOnCount(int count) {
        return new ArrayList (transactions.subList (0, Math.min (count, transactions.size ())));
    }

    public Common getOldCommon() {
        return oldCommon;
    }

    // добавление
    public void add(Common c) throws ModelException {
        List ref = getRef(c);
        // если такая запись уже есть, выбрасываем исключение
        if (ref.contains (c)) throw new ModelException (ModelException.IS_EXISTS);
        ref.add (c);
        c.postAdd (this); // adding
        sort (); // sorting
        saved = false;
    }

    public void edit(Common oldC, Common newC) throws ModelException {
        List ref = getRef (oldC);
        if (ref.contains (newC) && oldC != ref.get (ref.indexOf (newC))) throw new ModelException (ModelException.IS_EXISTS);
        ref.set (ref.indexOf (oldC), newC); // заменяем инекс старого обьекта на новый
        oldCommon = oldC;
        newC.postEdit (this);
        sort ();
        saved = false;
    }

    public void remove(Common c) {
        getRef (c).remove (c);
        c.postRemove (this);
        saved = false;
    }

    // определение источника
    private List getRef(Common c) {
        if (c instanceof Account) return accounts;
        else if (c instanceof Article) return articles;
        else if (c instanceof Currency) return currencies;
        else if (c instanceof Transaction) return transactions;
        else if (c instanceof Transfer) return transfers;
        return null;
    }

    @Override
    public String toString() {
        return "SaveData{" +
                "articles=" + articles +
                ", accounts=" + accounts +
                ", currencies=" + currencies +
                ", transactions=" + transactions +
                ", transfers=" + transfers +
                '}';
    }

    public void updateCurrencies() throws Exception {
        HashMap<String, Double> rates = RateCurrency.getRates (getBaseCurrency ());
        for (Currency c : currencies)
            c.setRate (rates.get(c.getCode ()));
        for (Account a : accounts)
            a.getCurrency ().setRate (rates.get(a.getCurrency ().getCode ()));
    }
}
