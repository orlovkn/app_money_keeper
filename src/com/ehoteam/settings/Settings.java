package com.ehoteam.settings;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

final public class Settings {

    public static final File FONT_ROBOTO_LIGHT = new File ("fonts/Roboto-Light.ttf");
    public static final File SAVE_DIR = new File ("saves/");
    public static final String SAVE_FILE_EXT = "kpr"; // расширение файлов приложения

    public static final String FORMAT_AMOUNT = "%.2f"; // форматирование цены
    public static final String FORMAT_RATE = "%.1f"; // форматирование валюты
    public static final String FORMAT_DATE = "dd.MM.yyyy"; // форматирование даты
    public static final String FORMAT_DATE_MONTH = "MMMM yyyy"; // форматирование месяца
    public static final String FORMAT_DATE_YEAR = "yyyy"; // форматирование года

    public static final int COUNT_OVERVIEW_ROWS = 10; // кол-во отображаемых транзакций

    public static final String[] CURRENCIES_CODES = new String[]{"RUB", "USD", "EUR"};

    private static final File FILE_SETTINGS = new File ("saves/settings.ini");
    private static File FILE_SAVE = new File ("saves/default.kpr");

    // инициализация
    public static void init() {
        try {
            Ini ini = new Ini(FILE_SETTINGS);
            Preferences prefs = new IniPreferences (ini);
            String file = prefs.node("Settings").get("SAVE_FILE", null);
            if (file != null) FILE_SAVE = new File (file);

            setLocale();
        } catch (IOException e) {
            save();
        }
    }

    public static File getFileSave() {
        return FILE_SAVE;
    }

    public static void setFileSave(File file) {
        Settings.FILE_SAVE = file;
        save();
    }

    // сохранение файла
    private static void save() {
        try {
            Wini ini = new Wini (FILE_SETTINGS);
            if (FILE_SAVE != null) ini.put("Settings", "FILE_SAVE", FILE_SAVE.getAbsolutePath ().replace ("\\", "\\\\"));
            ini.store ();
        } catch (IOException e) {
            Logger.getLogger (Settings.class.getName ()).log (Level.SEVERE, null, e);
        }
    }

    private static void setLocale() {
        Locale.setDefault (new Locale ("ru"));
    }


}
