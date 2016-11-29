package com.example.gabrielcardoso.possogastar.model;

import com.example.gabrielcardoso.possogastar.db.DataBaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import static com.example.gabrielcardoso.possogastar.model.BaseAccount.ACCOUNT_TYPE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by dfavato on 29/11/16.
 */

@DatabaseTable
public class MoneyTransfer {
    private enum  TRANSFER_TYPE {
        EARNINGS,
        EXPENSES,
        TRANSFER
    }

    private static Dao<MoneyTransfer, Long> dao;

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(foreign = true, foreignColumnName = "ORIGIN", canBeNull = false)
    private BaseAccount origin;

    @DatabaseField(foreign = true, foreignColumnName = "DESTINY", canBeNull = false)
    private BaseAccount destiny;

    @DatabaseField
    private float value;

    @DatabaseField(foreign = true)
    private BasePaymentMethod paymentMethod;

    @DatabaseField
    private Calendar paymentDate;

    @DatabaseField
    private Calendar realDate;

    @DatabaseField
    private TRANSFER_TYPE type;

    MoneyTransfer() {
    }

    public MoneyTransfer(BaseAccount origin, BaseAccount destiny, BasePaymentMethod paymentMethod,
                         float value) {
        this();
        this.setOrigin(origin);
        this.setDestiny(destiny);
        this.setPaymentMethod(paymentMethod);
        this.setValue(value);
        this.setPaymentDate();
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
    private void setId(Long id) {
        this.id = id;
    }
    private void setRealDate(Calendar date) {
        this.realDate = date;
    }
    private void setPaymentDate() {
        this.paymentDate = this.getPaymentMethod().paymentDate();
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

    public void save() throws SQLException {
        dao.createOrUpdate(this);
    }

    public static void setDao(DataBaseHelper db) throws SQLException {
        dao = db.getDao(MoneyTransfer.class);
    }

    public static MoneyTransfer queryForId(Long id) throws SQLException {
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

}
