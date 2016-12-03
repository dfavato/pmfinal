package com.example.gabrielcardoso.possogastar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

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
        ArrayList<AccountItem> items = new ArrayList<>();
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        AccountItemAdapter.setAccountItemList(R.id.lista_contas_reais, items, this, this);
    }

    public void setAccountingAccountsList(){
        ArrayList<AccountItem> items = new ArrayList<>();
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        items.add(new AccountItem("Titulo",0.0f,new Date(),0));
        AccountItemAdapter.setAccountItemList(R.id.lista_contas_contabeis, items, this, this);
    }

    public void setRealAccountDisplayButtons(){
        //
    }

    public void setAccountingAccountDisplayButtons(){
        //
    }


}
