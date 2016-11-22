package com.example.gabrielcardoso.possogastar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BankStatement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_statement);
        getActionBar().setDisplayHomeAsUpEnabled(true); //seta a opção de exibir a seta de voltar para true
    }
}
