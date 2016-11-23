package com.example.gabrielcardoso.possogastar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AccountItemDetailed extends AppCompatActivity {
    //valores so pra mostrar
    private int mAccountId = 0;
    private String mAccountTitle = "Carteira";
    private double mAmount = 70.00;
    private String mLastUse = "09/06/2016";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_item_detailed);
        getActionBar().setDisplayHomeAsUpEnabled(true); //seta a opção de exibir a seta de voltar para true
        //
        //TODO trabalhar para pegar account id por intent
        //TODO pesquisar dados da conta no banco de dados
        //
        displayAccountInfo();
        displayLastTransitions();
    }

    public void displayAccountInfo(){
        ((TextView)findViewById(R.id.account_title)).setText(this.mAccountTitle);
        ((TextView)findViewById(R.id.amount)).setText(String.valueOf(this.mAmount));
        ((TextView)findViewById(R.id.last_activity_date)).setText(this.mLastUse);
    }

    public void displayLastTransitions(){
        ListView listView = (ListView) findViewById(R.id.list_view);
        ArrayList<BankTransition> transitions = getLastTransitions();
        BankTransitionAdapter transitionAdapter = new BankTransitionAdapter(this,transitions);
        listView.setAdapter(transitionAdapter);
    }

    public ArrayList<BankTransition> getLastTransitions(){
        //TODO pegar as ultimas transações envolvendo essa conta no banco de dados
        ArrayList<BankTransition> bankTransitions = new ArrayList<>();
        bankTransitions.add(new BankTransition("01/06/2016","08:00",15,"Lanchonete da faculdade",mAccountId,"Carteira",8.50,mAccountId));
        bankTransitions.add(new BankTransition("03/06/2016","11:00",mAccountId,"Carteira",15,"conta banco do brasil",50.00,mAccountId));
        bankTransitions.add(new BankTransition("05/06/2016","18:00",17,"Gasolina",mAccountId,"Carteira",50,mAccountId));
        bankTransitions.add(new BankTransition("09/06/2016","12:30",mAccountId,"Carteira",15,"conta banco do brasil",70.00,mAccountId));
        return bankTransitions;
    }
}
