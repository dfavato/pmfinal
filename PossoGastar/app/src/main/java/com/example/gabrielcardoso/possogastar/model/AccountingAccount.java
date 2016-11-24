package com.example.gabrielcardoso.possogastar.model;


import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * Created by dfavato on 19/11/16.
 */

public class AccountingAccount extends AbstractAccount {
    private float budget;

    private AccountingAccount parentAccount;

    private ForeignCollection<AccountingAccount> childrenAccounts;

    public AccountingAccount(){
        this.budget = 0;
        this.parentAccount = null;
    }

    public AccountingAccount(String name, float budget){
        this();
        this.setBudget(budget);
        this.setName(name);
    }

    void setBudget(Float budget) {
        float amount = budget  - this.budget;
        this.budget = budget;
        this.updateParentAccountBudget(budget);
    }

    public void changeBugdet(Float amount) {
        this.budget += amount;
        this.updateParentAccountBudget(amount);
    }

    public void setParentAccount(AccountingAccount account) {
        if(account != this) {
            this.parentAccount = account;
        } else {
            //TODO throw erro, uma conta não pode ser pai dela mesma
        }
        this.updateParentAccountBudget(this.budget);
    }

    private void updateParentAccountBudget(Float amount) {
        if(this.parentAccount != null) {
            this.parentAccount.changeBugdet(amount);
        }
    }

    @Override
    public String toString() {
        String str = super.toString();
        if(this.parentAccount != null) {
            str += " -> " + this.parentAccount.toString();
        }
        return str;
    }
}
