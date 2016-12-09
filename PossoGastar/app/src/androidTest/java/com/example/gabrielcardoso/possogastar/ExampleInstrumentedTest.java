package com.example.gabrielcardoso.possogastar;

import android.content.Context;
import android.icu.util.Calendar;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.gabrielcardoso.possogastar.db.DataBaseHelper;
import com.example.gabrielcardoso.possogastar.model.AccountingAccount;
import com.example.gabrielcardoso.possogastar.model.BaseAccount;
import com.example.gabrielcardoso.possogastar.model.BasePaymentMethod;
import com.example.gabrielcardoso.possogastar.model.Card;
import com.example.gabrielcardoso.possogastar.model.Cash;
import com.example.gabrielcardoso.possogastar.model.MoneyTransfer;
import com.example.gabrielcardoso.possogastar.model.RealAccount;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
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
    public void testAccountCreation() throws  Exception {
        DataBaseHelper db = new DataBaseHelper(InstrumentationRegistry.getTargetContext());
        BaseAccount.setDao(db);
        AccountingAccount acc1 = new AccountingAccount("Alimentação", 100);
        AccountingAccount acc2 = new AccountingAccount("Jantar", 50, acc1);
        AccountingAccount acc3 = new AccountingAccount("Alimentação", 100);
        RealAccount rac1 = new RealAccount("Santander", BaseAccount.REAL_TYPE.CHECKING_ACCOUNT);
        RealAccount rac2 = new RealAccount("Itaú", BaseAccount.REAL_TYPE.SAVINGS);

        acc1.save();
        acc2.save();
        rac1.save();
        rac2.save();

        assertEquals(BaseAccount.queryAll().size(), 4);
        assertEquals(RealAccount.queryAll().size(), 2);
        assertEquals(AccountingAccount.queryAll().size(), 2);
        assertEquals(AccountingAccount.queryForId(acc1.getId()).getId(), acc1.getId());
        assertEquals(RealAccount.queryForId(rac1.getId()).getName(), rac1.getName());
        assertEquals(AccountingAccount.queryForId(acc2.getId()).getParentAccount(), acc2.getParentAccount());

    }

    @Test
    public void testPaymentMethodCreation() throws Exception {
        DataBaseHelper db = new DataBaseHelper(InstrumentationRegistry.getTargetContext());
        BasePaymentMethod.setDao(db);
        Card c1 = new Card("Mastercard", 100, 1, 25);
        Card c2 = new Card("Visa", 200, 2, 26);
        Cash dinheiro = new Cash("Carteira");
        Cash cofre = new Cash("Cofre");

        c1.save();
        c2.save();
        dinheiro.save();
        cofre.save();

        Log.d("Teste", c1.toString());
        Log.d("Teste", c2.toString());
        Log.d("Teste", dinheiro.toString());
        Log.d("Teste", cofre.toString());

        assertEquals(BasePaymentMethod.queryAll().size(), 4);
        assertEquals(Cash.queryAll().size(), 2);
        assertEquals(Card.queryAll().size(), 2);
        assertEquals(c1.getId(), Card.queryForId(c1.getId()).getId());
        assertNull(Cash.queryForId(c1.getId()));


    }

    @Test
    public void testMoneyTransferCreation() throws Exception {
        DataBaseHelper db = new DataBaseHelper(InstrumentationRegistry.getTargetContext());
        MoneyTransfer.setDao(db);
        BaseAccount.setDao(db);
        BasePaymentMethod.setDao(db);

        BaseAccount destiny = AccountingAccount.queryAll().get(0);
        BaseAccount origin = RealAccount.queryAll().get(0);
        BasePaymentMethod method = Card.queryAll().get(0);

        MoneyTransfer transfer = new MoneyTransfer(origin, destiny, method, 10, new Date(GregorianCalendar.getInstance().get(Calendar.DATE)));

        transfer.save();

        assertEquals(MoneyTransfer.queryForId(transfer.getId()).getDestiny().getId(), destiny.getId());
        assertEquals(MoneyTransfer.queryForId(transfer.getId()).getType(), MoneyTransfer.TRANSFER_TYPE.EXPENSES);
        assertEquals(MoneyTransfer.queryForId(transfer.getId()).getOrigin().getName(), origin.getName());

    }

    @Test
    public void testarExtratoSaldo() throws Exception {
        DataBaseHelper db = new DataBaseHelper(InstrumentationRegistry.getTargetContext());
        setDaos(db);
        Date hoje = new Date(GregorianCalendar.getInstance().get(Calendar.DATE));
        BaseAccount acc = AccountingAccount.queryAll().get(0);
        List<MoneyTransfer> stmt = acc.statement(new Date(0), hoje);
        for(MoneyTransfer t: stmt) {
            Log.d("Extrato", t.toString());
        }
        Log.d("Saldo", acc.saldo(hoje)+"");
    }

    private void setDaos(DataBaseHelper db) throws SQLException {
        BaseAccount.setDao(db);
        BasePaymentMethod.setDao(db);
        MoneyTransfer.setDao(db);
    }

}
