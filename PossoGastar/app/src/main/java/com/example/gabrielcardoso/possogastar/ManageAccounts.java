package com.example.gabrielcardoso.possogastar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gabrielcardoso.possogastar.model.AccountingAccount;
import com.example.gabrielcardoso.possogastar.model.RealAccount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageAccounts extends AppCompatActivity {
    /*O que fiz ate agr: criei um layout pra mostrar a lista de contas de cada tipo. A ideia é esconder e mostrar as listas quando
    se clica nas setinhas pra baixo e pra cima.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);
        setRealAccountsList();
        setAccountingAccountsList();
        setRealAccountDisplayButtons();
        setAccountingAccountDisplayButtons();
    }

    public void setRealAccountsList(){
        RealAccount realAccount = new RealAccount();
        AccountItem accountItem;
        ArrayList<AccountItem> items = new ArrayList<>();
        List<RealAccount> contasReais;

        try {
            contasReais = realAccount.queryAll();
            if(contasReais.size() > 0) {
                for(int i = 0; i < contasReais.size(); i++) {
                    accountItem = new AccountItem(contasReais.get(i).getName(),
                            contasReais.get(i).saldo(new Date()), contasReais.get(i).lastUsed(), contasReais.get(i).getId());

                    items.add(accountItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        /**items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));*/
        AccountItemAdapter.setAccountItemList(R.id.lista_contas_reais, items, this, this);
    }

    public void setAccountingAccountsList(){
        AccountingAccount accountingAccount = new AccountingAccount();
        AccountItem accountItem;
        ArrayList<AccountItem> items = new ArrayList<>();
        List<AccountingAccount> contasContabeis;

        try {
            contasContabeis = accountingAccount.queryAll();
            if(contasContabeis.size() > 0) {
                for(int i = 0; i < contasContabeis.size(); i++) {
                    accountItem = new AccountItem(contasContabeis.get(i).getName(), contasContabeis.get(i).saldo(new Date()),
                            contasContabeis.get(i).lastUsed(), contasContabeis.get(i).getId());

                    items.add(accountItem);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));*/
        AccountItemAdapter.setAccountItemList(R.id.lista_contas_contabeis, items, this, this);
    }

    public void setRealAccountDisplayButtons(){
        //
    }

    public void setAccountingAccountDisplayButtons(){
        //
    }


}
