package com.example.gabrielcardoso.possogastar.model;


import android.content.Context;

import com.example.gabrielcardoso.possogastar.db.DataBaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by dfavato on 20/11/16.
 */

@DatabaseTable
public class BasePaymentMethod {
    enum PaymentType {
        CARD,
        CASH
    }
    public static DataBaseHelper db = null;
    private static Dao<BasePaymentMethod, Long> dao = null;

    @DatabaseField(generatedId = true)
    private Long id;

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

    BasePaymentMethod(){
    }
    public BasePaymentMethod(String name) {
        this.setName(name);
        this.limit = 0;
        this.closeDate = 0;
        this.dueDate = 0;
    }

    public static void setDao(Context context) throws SQLException {
        if(dao == null) {
            dao = db.getDao(BasePaymentMethod.class);
        }
    }

    void setId(Long id) {
        this.id = id;
    }
    void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public Long getId() {
        return id;
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

    public Calendar paymentDate() {
        return GregorianCalendar.getInstance();
    }

    public static BasePaymentMethod queryForId(Long id) throws SQLException {
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
