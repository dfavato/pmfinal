package com.example.gabrielcardoso.possogastar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewRealAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_real_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Spinner spinner = (Spinner) findViewById(R.id.tipos_conta);
        setSupportActionBar(toolbar);
        // Cria um ArrayAdapter usando o array de strings de opções.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.account_types_array, android.R.layout.simple_spinner_item);
        // Especifica o layout a ser usado quando a lista aparece.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplica o adapter ao spinner.
        spinner.setAdapter(adapter);
    }

}
