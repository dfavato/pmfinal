package com.example.gabrielcardoso.possogastar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TransferMoney extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);
        getActionBar().setDisplayHomeAsUpEnabled(true); //seta a opção de exibir a seta de voltar para true
    }
}
