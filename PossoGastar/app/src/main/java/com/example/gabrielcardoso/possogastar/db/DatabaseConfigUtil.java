package com.example.gabrielcardoso.possogastar.db;



import com.example.gabrielcardoso.possogastar.model.BaseAccount;
import com.example.gabrielcardoso.possogastar.model.BasePaymentMethod;
import com.example.gabrielcardoso.possogastar.model.MoneyTransfer;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;

/**
 * Created by dfavato on 19/11/16.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    //Classes que devem ser transformadas em tabelas
    public static final Class<?>[] CLASSES = new Class[] {
        BaseAccount.class,
        BasePaymentMethod.class,
        MoneyTransfer.class
    };

    public static void main(String[] args) {
        try {
            writeConfigFile(new File("ormlite_config.txt"), CLASSES);
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }
    }
}
