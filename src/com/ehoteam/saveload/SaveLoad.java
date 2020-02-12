package com.ehoteam.saveload;

import com.ehoteam.settings.Settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class SaveLoad {

    public static void load(SaveData sd) {
        try {
            JAXBContext context = JAXBContext.newInstance (Wrapper.class);
            Unmarshaller um = context.createUnmarshaller ();

            Wrapper wrapper =  (Wrapper) um.unmarshal (Settings.getFileSave ());

            sd.setAccounts (wrapper.getAccounts ());
            sd.setArticles (wrapper.getArticles ());
            sd.setCurrencies (wrapper.getCurrencies ());
            sd.setTransactions (wrapper.getTransactions ());
            sd.setTransfers (wrapper.getTransfers ());
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static void save(SaveData sd) {
        try {
            JAXBContext context = JAXBContext.newInstance (Wrapper.class);
            Marshaller m = context.createMarshaller ();
            m.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true); // перенос строки

            Wrapper wrapper = new Wrapper ();

            wrapper.setAccounts (sd.getAccounts ());
            wrapper.setArticles (sd.getArticles ());
            wrapper.setCurrencies (sd.getCurrencies ());
            wrapper.setTransactions (sd.getTransactions ());
            wrapper.setTransfers (sd.getTransfers ());

            m.marshal (wrapper, Settings.getFileSave ());

        } catch (JAXBException e) {
            e.printStackTrace ();
        }
    }

}
