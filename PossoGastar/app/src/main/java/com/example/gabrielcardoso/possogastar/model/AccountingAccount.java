package com.example.gabrielcardoso.possogastar.model;



import android.support.annotation.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfavato on 26/11/16.
 */

public class AccountingAccount extends BaseAccount {
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

    private void setParentAccount(AccountingAccount parent) {
        this.parentAccount = parent;
    }
    public AccountingAccount getParentAccount() {
        return (AccountingAccount) this.parentAccount;
    }

    private void updateBudget(float amount) {
        this.budget += amount;
        if(this.getParentAccount() != null) {
            this.getParentAccount().updateBudget(amount);
        }
        //TODO update childrenAccounts budget
    }

    private void setBudget(float budget) {
        this.budget = budget;
    }
    public float getBudget() {
        //TODO get children budgets
        return this.budget;
    }

    @Nullable
    public static AccountingAccount queryForId(Long id) throws SQLException {
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

    @Override
    public String toString() {
        return this.getId() + " - " + this.getName() + " (" + this.getBudget() + ") " + (this.getParentAccount() == null ? "" : this.getParentAccount().getId()+"");
    }
}
