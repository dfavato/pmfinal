package com.example.gabrielcardoso.possogastar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gabrielcardoso.possogastar.model.BaseAccount;
import com.example.gabrielcardoso.possogastar.model.RealAccount;

import java.sql.SQLException;
import java.text.NumberFormat;

public class NewRealAccount extends AppCompatActivity implements View.OnClickListener{
    EditText nomeConta;
    Spinner spinner;
    Button botaoCancelar, botaoSalvar;
    String nomeTexto, intentExtra;
    ArrayAdapter<BaseAccount.REAL_TYPE> adapter;
    View.OnClickListener handlerCancelar, handlerSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_real_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        intentExtra = getIntent().getStringExtra("origem");
        nomeConta = (EditText) findViewById(R.id.nomeContaReal);
        spinner = (Spinner) findViewById(R.id.tipos_conta);
        botaoCancelar = (Button) findViewById(R.id.cancelar_cadastro_conta_real);
        botaoSalvar = (Button) findViewById(R.id.salvar_conta_real);

        setSupportActionBar(toolbar);
        preencheSpinner(this);

        botaoCancelar.setOnClickListener(this);
        botaoSalvar.setOnClickListener(this);
    }

    public void preencheSpinner(Context context) {
        // Cria um ArrayAdapter usando o enum de contas reais em BaseAccount.
        adapter = new ArrayAdapter<BaseAccount.REAL_TYPE>(context,
                android.R.layout.simple_spinner_item, BaseAccount.REAL_TYPE.values());
        // Especifica o layout a ser usado quando a lista aparece.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplica o adapter ao spinner.
        spinner.setAdapter(adapter);
    }

    public boolean camposObrigatoriosPreenchidos() {
        nomeTexto = nomeConta.getText().toString();

        if(nomeTexto != null && !nomeTexto.isEmpty() && !nomeTexto.equals(" "))
            return true;

        return false;
    }

    @Override
    public void onClick(View v) {
        if(v == botaoCancelar) {
            redirecionaTela();
        }
        else if(v == botaoSalvar) {
            if(camposObrigatoriosPreenchidos()) {
                RealAccount novaConta = new RealAccount(nomeTexto, (BaseAccount.REAL_TYPE) spinner.getSelectedItem());
                try {
                    novaConta.save();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast toast = Toast.makeText(NewRealAccount.this, "Conta salva com sucesso!",
                        Toast.LENGTH_SHORT);
                toast.show();

                redirecionaTela();
            }
            else {
                Toast toast = Toast.makeText(NewRealAccount.this, "Por favor, preencha todos os campos obrigatórios.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void redirecionaTela() {
        Intent intent;

        if(intentExtra.equals("main")) {
            intent = new Intent(NewRealAccount.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
