package com.example.gabrielcardoso.possogastar.model;



import android.content.Context;

import com.example.gabrielcardoso.possogastar.db.DataBaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;


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

    static Dao<BaseAccount, Long> dao = null;

    @DatabaseField(generatedId = true)
    private Long id;

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

    void setId(Long accountId) {
        this.id = accountId;
    }
    void setName(String name) {
        this.name = name;
    }
    public Long getId() {
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

    public static BaseAccount queryForId(Long id) throws SQLException {
        return dao.queryForId(id);
    }

    public static List<?> queryAll() throws SQLException {
        return dao.queryForAll();
    }

    public static List<?> queryForField(String fieldName, Object value) throws SQLException {
        return dao.queryForEq(fieldName, value);
    }

    public List<MoneyTransfer> extrato(Calendar begin, Calendar end) throws SQLException {
        return MoneyTransfer.queryAllForAccount(this);
    }

}
