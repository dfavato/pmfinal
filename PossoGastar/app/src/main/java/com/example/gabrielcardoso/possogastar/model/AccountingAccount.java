package com.example.gabrielcardoso.possogastar.model;



import android.support.annotation.Nullable;

import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dfavato on 26/11/16.
 */

public class AccountingAccount extends BaseAccount {
    //Construtores
    public AccountingAccount(String name, float budget, AccountingAccount parent) {
        super(name);
        this.accountType = ACCOUNT_TYPE.ACCOUNTING;
        this.setBudget(budget);
        this.setParentAccount(parent);
    }
    public AccountingAccount(String name, float budget) {
        this(name, budget, null);
    }
    public AccountingAccount(BaseAccount base) {
        this(base.getName(), base.budget);
        if(base.parentAccount != null) {
            this.setParentAccount(new AccountingAccount(base.parentAccount));
        }
        this.setId(base.getId());
    }
    public AccountingAccount() {}

    //Getters e Setters
    private void setParentAccount(AccountingAccount parent) {
        this.parentAccount = parent;
    }
    public AccountingAccount getParentAccount() {
        return (AccountingAccount) this.parentAccount;
    }
    private void setBudget(float budget) {
        this.budget = budget;
    }
    public float getBudget() {
        //TODO get children budgets
        return this.budget;
    }
    public List<AccountingAccount> getChildrenAccount () throws SQLException {
        List<AccountingAccount> children = new ArrayList<>();
        for(BaseAccount b: (List<BaseAccount>) BaseAccount.queryForField("parentAccount_id", this)) {
            children.add(new AccountingAccount(b));
        }
        return children;
    }

    //Static methods
    @Nullable
    public static AccountingAccount queryForId(Integer id) throws SQLException {
        BaseAccount b = BaseAccount.queryForId(id);
        if(b != null && b.accountType == ACCOUNT_TYPE.ACCOUNTING) {
            return new AccountingAccount(b);
        }
        return null;
    }

    public static List<AccountingAccount> queryAll() throws SQLException {
        List<AccountingAccount> list = new ArrayList<>();
        for(BaseAccount b: (List<BaseAccount>) BaseAccount.queryForField("accountType", ACCOUNT_TYPE.ACCOUNTING)) {
            list.add(new AccountingAccount(b));
        }
        return list;
    }

    public static List<AccountingAccount> queryAllParent() throws SQLException {
        List<AccountingAccount> list = queryAll();
        AccountingAccount acc;
        for(Iterator<AccountingAccount> iterator = list.iterator(); iterator.hasNext();) {
            acc = iterator.next();
            if(acc.getParentAccount() != null) {
                iterator.remove();
            }
        }
        return list;
    }

    //Other methods
    private void updateBudget(float amount) {
        this.budget += amount;
        if(this.getParentAccount() != null) {
            this.getParentAccount().updateBudget(amount);
        }
        if(amount < 0) {
            //TODO update childrenAccounts budget
        }
    }

    @Override
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
        //TODO somar saldo de contas filhas
        for(AccountingAccount acc: this.getChildrenAccount()) {
            saldoAtual += acc.saldo(begin, end);
        }
        return  saldoAtual;
    }

    @Override
    public String toString() {
        return this.getId() + " - " + this.getName() + " (" + this.getBudget() + ") " + (this.getParentAccount() == null ? "" : this.getParentAccount().getId()+"");
    }

}
