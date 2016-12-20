package com.example.gabrielcardoso.possogastar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import java.util.ArrayList;

public class ManagePaymentMethods extends AppCompatActivity{

    FloatingActionButton botaoAddMetodo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_methods);
        //setPaymentMethodsList();

        botaoAddMetodo = (FloatingActionButton) findViewById(R.id.addMetodoPagamento);
        botaoAddMetodo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(ManagePaymentMethods.this, NewPaymentMethod.class);
                ManagePaymentMethods.this.startActivity(intent);
            }
        });
    }

    /*public void setPaymentMethodsList() {
        ArrayList<BasePaymentMethod> methods = new ArrayList<>();

        PaymentMethodAdapter.setPaymentMethodList(R.id.listaMetodosPagamento, methods, this, this);

    }*/


}
