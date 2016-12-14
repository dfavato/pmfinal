package com.example.gabrielcardoso.possogastar.model;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.gabrielcardoso.possogastar.db.DataBaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by dfavato on 20/11/16.
 */


public class Card extends BasePaymentMethod {
    Card() {
    }
    public Card(String name, Float limit, byte dueDate, byte closeDate) {
        super(name);
        this.paymentType = PaymentType.CARD;
        setLimit(limit);
        setCloseDate(closeDate);
        setDueDate(dueDate);
    }
    public Card(String name, int limit, int dueDate, int closeDate) {
        this(name, (float)limit, (byte)dueDate, (byte)closeDate);
    }
    private Card(BasePaymentMethod base) {
        this(base.getName(), base.limit, base.dueDate, base.closeDate);
        this.setId(base.getId());
    }

    void setDueDate(byte dueDate) {
        this.dueDate = dueDate;
    }
    void setCloseDate(byte closeDate) {
        this.closeDate = closeDate;
    }
    void setLimit(Float limit) {
        this.limit = limit;
    }
    byte getDueDate() {
        return this.dueDate;
    }
    byte getCloseDate() {
        return this.closeDate;
    }

    @Override
    public Date paymentDate(Date real) {
        Calendar date = Calendar.getInstance();
        Calendar cReal = Calendar.getInstance();
        cReal.setTime(real);
        date.set(Calendar.DAY_OF_MONTH, this.closeDate);
        date.set(Calendar.MONTH, cReal.get(Calendar.MONTH));
        if(cReal.get(Calendar.DAY_OF_MONTH) > this.closeDate) {
            //fatura fechada pagamento só no vencimento do próximo mês
            date.add(Calendar.MONTH, 1);
        }
        return date.getTime();
    }


    public static Card queryForId(Integer id) throws SQLException {
        BasePaymentMethod b = BasePaymentMethod.queryForId(id);
        if(b != null) {
            if(b.paymentType == PaymentType.CARD) {
                Card c = new Card(b);
                return c;
            }
        }
        return null;
    }


    public static List<Card> queryAll() throws SQLException {
        List<Card> list = new ArrayList<Card>();
        for(BasePaymentMethod b: (List<BasePaymentMethod>) BasePaymentMethod.queryForField("paymentType", PaymentType.CARD)) {
            list.add(new Card(b));
        }
        return list;
    }

}
