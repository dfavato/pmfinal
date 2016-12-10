package com.example.gabrielcardoso.possogastar.model;



import android.content.Context;

import com.example.gabrielcardoso.possogastar.db.DataBaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by dfavato on 19/11/16.
 */

@DatabaseTable
public class BaseAccount {
    enum ACCOUNT_TYPE {
        REAL,
        ACCOUNTING
    }
    public enum REAL_TYPE {
        CHECKING_ACCOUNT,
        SAVINGS,
        FUND,
        MONEY
    }

    static Dao<BaseAccount, Integer> dao = null;

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    ACCOUNT_TYPE accountType;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(foreign = true, canBeNull = true)
    BaseAccount parentAccount;

    @ForeignCollectionField
    ForeignCollection<BaseAccount> childrenAccount;

    @DatabaseField(canBeNull = true)
    float budget;

    @DatabaseField(canBeNull = true)
    REAL_TYPE realType;


    BaseAccount(){
        this.setName(null);
        this.budget = 0;
        this.parentAccount = null;
    }
    public BaseAccount(String name) {
        this();
        this.setName(name);
    }

    void setId(Integer accountId) {
        this.id = accountId;
    }
    void setName(String name) {
        this.name = name;
    }
    public Integer getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public ACCOUNT_TYPE getAccountType() {
        return this.accountType;
    }

    @Override
    public String toString() {
        return this.getId() + " - " + this.getName();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass() && this.getId().equals(((AccountingAccount)obj).getId());
    }

    public static void setDao(DataBaseHelper db) throws SQLException {
        if(dao == null) {
            dao = db.getDao(BaseAccount.class);
        }
    }

    public void save() throws SQLException {
        List<BaseAccount> accounts = dao.queryForEq("name", this.getName());
        if(accounts.isEmpty()) {
            dao.create(this);
        } else {
            this.setId(accounts.get(0).getId());
            dao.update(this);
        }
    }

    public static BaseAccount queryForId(Integer id) throws SQLException {
        return dao.queryForId(id);
    }

    public static List<?> queryAll() throws SQLException {
        return dao.queryForAll();
    }

    public static List<?> queryForField(String fieldName, Object value) throws SQLException {
        return dao.queryForEq(fieldName, value);
    }

    public List<MoneyTransfer> statement(Date begin, Date end) throws SQLException {
        QueryBuilder<MoneyTransfer, Integer> qb = MoneyTransfer.dao.queryBuilder();
        Where where = qb.where();
        where.between("paymentDate", begin, end);
        where.and();
        where.eq("origin", this);
        where.or();
        where.eq("destiny", this);
        PreparedQuery<MoneyTransfer> pq = qb.prepare();
        return MoneyTransfer.dao.query(pq);
    }

    public float saldo(Date date) throws SQLException {
        return saldo(new Date(0), date);
    }

    public float saldo(Date begin, Date end) throws SQLException {
        float saldoAtual = 0;
        List<MoneyTransfer> transfers = this.statement(begin, end);
        for(MoneyTransfer t: transfers) {
            if(this.equals(t.getDestiny())) {
                saldoAtual += t.getValue();
            } else {
                saldoAtual -= t.getValue();
            }
        }
        return  saldoAtual;
    }

    public boolean equals(BaseAccount acc) {
        return this.getId() == acc.getId() && this.accountType == this.accountType;
    }
}
