package com.example.gabrielcardoso.possogastar.db;

import com.example.gabrielcardoso.possogastar.model.AccountingAccount;
import com.example.gabrielcardoso.possogastar.model.Card;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;


/**
 * Created by dfavato on 19/11/16.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    //Classes que devem ser transformadas em tabelas
    public static final Class<?>[] CLASSES = new Class[] {
        AccountingAccount.class,
        Card.class
    };

    public static void main(String[] args) throws Exception {
        writeConfigFile(new File("ormlite_config.txt"), CLASSES);
    }
}
