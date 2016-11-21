package com.example.gabrielcardoso.possogastar;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.gabrielcardoso.possogastar.db.DataBaseHelper;
import com.example.gabrielcardoso.possogastar.model.AccountingAccount;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.gabrielcardoso.possogastar", appContext.getPackageName());
    }

    @Test
    public void useOrm() throws  Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataBaseHelper db = OpenHelperManager.getHelper(appContext, DataBaseHelper.class);

        Dao<Object, Long> dao = db.daoManager(AccountingAccount.class);
        AccountingAccount acc = (AccountingAccount) dao.queryForId(new Long(1));
        acc.setParentAccount((AccountingAccount) dao.queryForId(new Long(2)));
        dao.update(acc);
//        dao.create(acc);
//        System.out.print(acc.toString());

        List<Object> listacc = dao.queryForAll();
        for(Object a : listacc) {
            Log.d("Teste", a.toString());
        }
    }
}
