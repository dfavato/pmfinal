package com.example.gabrielcardoso.possogastar.model;


import android.content.Context;

import com.example.gabrielcardoso.possogastar.db.DataBaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by dfavato on 20/11/16.
 */

@DatabaseTable
public class BasePaymentMethod {
    public enum PaymentType {
        CARD,
        CASH
    }
    private static Dao<BasePaymentMethod, Integer> dao = null;

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(unique = true, canBeNull = false)
    private String name;

    @DatabaseField
    PaymentType paymentType;

    @DatabaseField(canBeNull = false)
    float limit;

    @DatabaseField(canBeNull = false)
    byte dueDate; //vencimento

    @DatabaseField(canBeNull = false)
    byte closeDate; //data de fechamento da fatura

    public BasePaymentMethod(){
    }
    public BasePaymentMethod(String name) {
        this.setName(name);
        this.limit = 0;
        this.closeDate = 0;
        this.dueDate = 0;
    }
    public BasePaymentMethod(String name, PaymentType paymentType, float limit, byte dueDate, byte closeDate){
        this(name);
        setPaymentType(paymentType);
        this.limit = limit;
        this.dueDate = dueDate;
        this.closeDate = closeDate;
    }


    public static void setDao(DataBaseHelper db) throws SQLException {
        if(dao == null) {
            dao = db.getDao(BasePaymentMethod.class);
        }
    }

    void setId(Integer id) {
        this.id = id;
    }
    void setName(String name) {
        this.name = name;
    }
    private void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
    public String getName() {
        return this.name;
    }
    public Integer getId() {
        return id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void save() throws SQLException {
        List<?> payments = BasePaymentMethod.queryForField("name", this.getName());
        if(payments.isEmpty()) {
            dao.create(this);
        } else {
            BasePaymentMethod b = (BasePaymentMethod) payments.get(0);
            this.setId(b.getId());
            dao.update(this);
        }
    }

    public Date paymentDate(Date real) {
        return real;
    }

    public static BasePaymentMethod queryForId(Integer id) throws SQLException {
        return dao.queryForId(id);
    }

    public static List<?> queryAll() throws SQLException {
        return dao.queryForAll();
    }

    public static List<?> queryForField(String fieldName, Object value) throws SQLException {
        return dao.queryForEq(fieldName, value);
    }

    @Override
    public String toString() {
        return this.getId() + " - " + this.getName();
    }
}
