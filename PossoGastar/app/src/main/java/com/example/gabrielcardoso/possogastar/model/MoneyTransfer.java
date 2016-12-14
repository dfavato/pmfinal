package com.example.gabrielcardoso.possogastar.model;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import com.example.gabrielcardoso.possogastar.db.DataBaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.gabrielcardoso.possogastar.model.BaseAccount.ACCOUNT_TYPE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by dfavato on 29/11/16.
 */

@DatabaseTable
public class MoneyTransfer {
    public enum  TRANSFER_TYPE {
        EARNINGS,
        EXPENSES,
        TRANSFER
    }

    public static Dao<MoneyTransfer, Integer> dao = null;

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true, columnName = "ORIGIN", canBeNull = false, foreignAutoRefresh = true)
    private BaseAccount origin;

    @DatabaseField(foreign = true, columnName = "DESTINY", canBeNull = false, foreignAutoRefresh = true)
    private BaseAccount destiny;

    @DatabaseField
    private float value;

    @DatabaseField(foreign = true)
    private BasePaymentMethod paymentMethod;

    @DatabaseField
    private Date paymentDate;

    @DatabaseField
    private Date realDate;

    @DatabaseField
    private TRANSFER_TYPE type;

    MoneyTransfer() {
    }

    public MoneyTransfer(BaseAccount origin, BaseAccount destiny, BasePaymentMethod paymentMethod,
                         float value, Date day) {
        this();
        this.setOrigin(origin);
        this.setDestiny(destiny);
        this.setPaymentMethod(paymentMethod);
        this.setValue(value);
        this.setPaymentDate(day);
        this.setRealDate(day);
        this.setType();
    }

    private void setOrigin(BaseAccount origin) {
        this.origin = origin;
    }
    private void setDestiny(BaseAccount destiny) {
        this.destiny = destiny;
    }
    private void setPaymentMethod(BasePaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    private void setValue(float value) {
        this.value = value;
    }
    private void setId(Integer id) {
        this.id = id;
    }
    private void setRealDate(Date date) {
        this.realDate = date;
    }
    private void setPaymentDate(Date day) {
        this.paymentDate = this.getPaymentMethod().paymentDate(day);
    }
    private void setType() {
        if(this.getOrigin().getAccountType() == ACCOUNT_TYPE.ACCOUNTING) {
            if(this.getDestiny().getAccountType() == ACCOUNT_TYPE.REAL) {
                //Origem contábil destino real = Renda
                this.type = TRANSFER_TYPE.EARNINGS;
            } else {
                //Origem e destino contábil não pode ocorrer
                //TODO raise error
            }
        } else {
            if(this.getDestiny().getAccountType() == ACCOUNT_TYPE.ACCOUNTING) {
                //Origem real e destino contábil = Despesa
                this.type = TRANSFER_TYPE.EXPENSES;
            } else {
                //Origem e destino real = Transfência entre contas
                this.type = TRANSFER_TYPE.TRANSFER;
            }
        }
    }

    public BaseAccount getDestiny() {
        return this.destiny;
    }
    public BaseAccount getOrigin() {
        return this.origin;
    }
    public BasePaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }
    public Integer getId() {
        return id;
    }
    public TRANSFER_TYPE getType() {
        return type;
    }
    public float getValue() {
        return value;
    }

    public Date getPaymentDate() {
        return this.paymentDate;
    }

    public void save() throws SQLException {
        dao.createOrUpdate(this);
    }

    public static void setDao(DataBaseHelper db) throws SQLException {
        if(dao == null) {
            dao = db.getDao(MoneyTransfer.class);
        }
    }

    public static MoneyTransfer queryForId(Integer id) throws SQLException {
        return dao.queryForId(id);
    }

    public static List<MoneyTransfer> queryAll() throws SQLException {
        return dao.queryForAll();
    }

    public static List<MoneyTransfer> queryForField(String fieldName, Object value) throws SQLException {
        return dao.queryForEq(fieldName, value);
    }

    public static List<MoneyTransfer> queryAllForAccount(BaseAccount account) throws SQLException {
        List<MoneyTransfer> list = new ArrayList<>();
        list = queryForField("origin", account);
        list.addAll(queryForField("destiny", account));
        return list;
    }

    @Override
    public String toString() {
        return "Valor: " + this.getValue() + " | Data:" +
                android.text.format.DateFormat.format("dd/MM/yy", this.getPaymentDate()) +
                " | De:" + this.getOrigin().getName() + " | Para:" + this.getDestiny().getName() +
                " | Pgt: " + this.getPaymentMethod().toString();

    }
}
