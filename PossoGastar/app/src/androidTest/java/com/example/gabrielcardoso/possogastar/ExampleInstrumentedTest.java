package com.example.gabrielcardoso.possogastar;

import android.content.Context;
import android.icu.util.Calendar;
import android.support.annotation.RequiresApi;
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
    public void testModelFunction() throws Exception {
        DataBaseHelper db = new DataBaseHelper(InstrumentationRegistry.getTargetContext());
        setDaos(db);
        testAccountCreation();
        testPaymentMethodCreation();
        testMoneyTransferCreation();
    }

    private void testAccountCreation() throws  Exception {
        AccountingAccount acc1 = new AccountingAccount("Alimentação", 100);
        AccountingAccount acc2 = new AccountingAccount("Jantar", 50, acc1);
        AccountingAccount acc3 = new AccountingAccount("Alimentação", 100);
        AccountingAccount acc4 = new AccountingAccount("Diversão", 20);
        AccountingAccount acc5 = new AccountingAccount("Cinema", 100, acc4);
        AccountingAccount acc6 = new AccountingAccount("Compras", 1600);
        AccountingAccount acc7 = new AccountingAccount("PS4", 0, acc6);
        AccountingAccount acc8 = new AccountingAccount("Jantar de Natal", 0, acc2);
        AccountingAccount acc9 = new AccountingAccount("Saldo Anterior", 0);
        AccountingAccount acc10 = new AccountingAccount("Teatro", 0, acc4);
        RealAccount rac1 = new RealAccount("Santander", BaseAccount.REAL_TYPE.CHECKING_ACCOUNT);
        RealAccount rac2 = new RealAccount("Itaú", BaseAccount.REAL_TYPE.SAVINGS);
        RealAccount rac3 = new RealAccount("Carteira", BaseAccount.REAL_TYPE.MONEY);


        acc1.save();
        acc2.save();
        acc3.save();
        acc4.save();
        acc5.save();
        acc6.save();
        acc7.save();
        acc8.save();
        acc9.save();
        acc10.save();
        rac1.save();
        rac2.save();
        rac3.save();

        assertEquals(AccountingAccount.queryForId(acc1.getId()).getId(), acc1.getId());
        assertEquals(RealAccount.queryForId(rac1.getId()).getName(), rac1.getName());
        assertEquals(AccountingAccount.queryForId(acc2.getId()).getParentAccount(), acc2.getParentAccount());

    }

    private void testPaymentMethodCreation() throws Exception {
        Card c1 = new Card("Mastercard", 100, 1, 25);
        Card c2 = new Card("Visa", 200, 2, 26);
        Cash dinheiro = new Cash("Dinheiro");

        c1.save();
        c2.save();
        dinheiro.save();


        Log.d("Teste", c1.toString());
        Log.d("Teste", c2.toString());
        Log.d("Teste", dinheiro.toString());

        assertEquals(BasePaymentMethod.queryAll().size(), 3);
        assertEquals(Cash.queryAll().size(), 1);
        assertEquals(Card.queryAll().size(), 2);
        assertEquals(c1.getId(), Card.queryForId(c1.getId()).getId());
        assertNull(Cash.queryForId(c1.getId()));

    }

    private void testMoneyTransferCreation() throws Exception {
        BaseAccount destiny, origin;
        BasePaymentMethod method;
        MoneyTransfer transfer;

        for(int j=0; j<3; j++) {
            origin = (BaseAccount) BaseAccount.queryForField("name", "Saldo Anterior").get(0);
            destiny = RealAccount.queryAll().get(j);
            method = (BasePaymentMethod) BasePaymentMethod.queryForField("name", "Dinheiro").get(0);
            transfer = new MoneyTransfer(origin, destiny, method, 1000*(j+1), GregorianCalendar.getInstance().getTime());
            transfer.save();
            for(int i=0; i<7;i++) {
                for(int k=0; k<3; k++) {
                    destiny = AccountingAccount.queryAll().get(i);
                    origin = RealAccount.queryAll().get(j);
                    method = (BasePaymentMethod) BasePaymentMethod.queryAll().get(k);
                    transfer = new MoneyTransfer(origin, destiny, method, (i+1)*(j+1)*(k+1), GregorianCalendar.getInstance().getTime());
                    transfer.save();
                    assertEquals(MoneyTransfer.queryForId(transfer.getId()).getDestiny().getId(), destiny.getId());
                    assertEquals(MoneyTransfer.queryForId(transfer.getId()).getOrigin().getName(), origin.getName());
                }

            }
        }
    }

    @Test
    public void testarExtratoSaldo() throws Exception {
        DataBaseHelper db = new DataBaseHelper(InstrumentationRegistry.getTargetContext());
        setDaos(db);
        Date hoje = GregorianCalendar.getInstance().getTime();
        List<RealAccount> accounts = RealAccount.queryAll();
        for(RealAccount acc: accounts) {
            List<MoneyTransfer> stmt = acc.statement(new Date(0), hoje);
            for(MoneyTransfer t: stmt) {
                Log.d("Extrato", t.toString());
            }
            Log.d("Saldo", acc.saldo(hoje)+"");
        }
        assertNull(null);
    }

    private void setDaos(DataBaseHelper db) throws SQLException {
        BaseAccount.setDao(db);
        BasePaymentMethod.setDao(db);
        MoneyTransfer.setDao(db);
    }

}
