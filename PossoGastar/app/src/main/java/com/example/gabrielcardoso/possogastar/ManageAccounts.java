package com.example.gabrielcardoso.possogastar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.gabrielcardoso.possogastar.model.AccountingAccount;
import com.example.gabrielcardoso.possogastar.model.RealAccount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageAccounts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);
        setRealAccountsList();
        setAccountingAccountsList();
        findViewById(R.id.adicionar_conta_real).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newAccount = new Intent(ManageAccounts.this,NewRealAccount.class);
                newAccount.putExtra("origem", "manage");
                startActivity(newAccount);
            }
        });
        findViewById(R.id.adicionar_conta_contabel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newAccount = new Intent(ManageAccounts.this,NewAccountingAccount.class);
                newAccount.putExtra("origem", "manage");
                startActivity(newAccount);
            }
        });
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

        if(items.size()==0){
            findViewById(R.id.lista_contas_reais).setVisibility(View.GONE);
            findViewById(R.id.contas_reais_placeHolder).setVisibility(View.VISIBLE);
        }else{
            AccountItemAdapter.setAccountItemList(R.id.lista_contas_reais, items, this, this);
        }
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

        if(items.size()==0){
            findViewById(R.id.lista_contas_contabeis).setVisibility(View.GONE);
            findViewById(R.id.contas_contabeis_placeHolder).setVisibility(View.VISIBLE);
        }else{
            AccountItemAdapter.setAccountItemList(R.id.lista_contas_contabeis, items, this, this);
        }
    }
}
