package com.example.gabrielcardoso.possogastar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabrielcardoso.possogastar.model.AccountingAccount;
import com.example.gabrielcardoso.possogastar.model.BaseAccount;
import com.example.gabrielcardoso.possogastar.model.BasePaymentMethod;
import com.example.gabrielcardoso.possogastar.model.Card;
import com.example.gabrielcardoso.possogastar.model.Cash;
import com.example.gabrielcardoso.possogastar.model.MoneyTransfer;
import com.example.gabrielcardoso.possogastar.model.RealAccount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransferMoney extends AppCompatActivity implements View.OnClickListener{
    private Button botaoSalvar, botaoCancelar;
    private Spinner spinnerContaOrigem, spinnerContaDestino, spinnerMetodos;
    private EditText valorTransferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);
        botaoSalvar = (Button) findViewById(R.id.finalizar_transferencia);
        botaoCancelar = (Button) findViewById(R.id.cancelar_transferencia);
        spinnerContaOrigem = (Spinner) findViewById(R.id.spinner_contas_reais);
        spinnerContaDestino = (Spinner) findViewById(R.id.spinner_contas_contabeis);
        spinnerMetodos = (Spinner) findViewById(R.id.spinner_metodo_pagamento);
        valorTransferencia = (EditText) findViewById(R.id.edit_valor_transferencia);

        try{
            setDestinyAccountSpinner(this);
            setOriginAccountSpinner(this);
            setPaymentMethodSpinner(this);
        }catch(SQLException e){
            Log.e("TransferMoney","SQL ERROR: "+e.toString());
        }

        botaoSalvar.setOnClickListener(this);
        botaoCancelar.setOnClickListener(this);

    }

    public void setOriginAccountSpinner(Context context) throws SQLException {
        List<String> nomeContas = new ArrayList<>();
        ArrayAdapter<String> adapter;
        List<?> contasCadastradas = BaseAccount.queryAll();

        nomeContas.add("-");

        for(int i = 0; i < contasCadastradas.size(); i++)
            nomeContas.add(((BaseAccount)contasCadastradas.get(i)).getName());

        // Cria um ArrayAdapter usando a lista de contas cadastradas.
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nomeContas);
        // Especifica o layout a ser usado quando a lista aparece.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplica o adapter ao spinner.
        ((Spinner)findViewById(R.id.spinner_contas_reais)).setAdapter(adapter);
    }

    public void setDestinyAccountSpinner(Context context) throws SQLException {
        List<String> nomeContas = new ArrayList<>();
        ArrayAdapter<String> adapter;
        List<?> contasCadastradas = BaseAccount.queryAll();

        nomeContas.add("-");

        for(int i = 0; i < contasCadastradas.size(); i++)
            nomeContas.add(((BaseAccount)contasCadastradas.get(i)).getName());

        // Cria um ArrayAdapter usando a lista de contas cadastradas.
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nomeContas);
        // Especifica o layout a ser usado quando a lista aparece.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplica o adapter ao spinner.
        ((Spinner)findViewById(R.id.spinner_contas_contabeis)).setAdapter(adapter);
    }
    public void setPaymentMethodSpinner(Context context) throws SQLException {
        Card card = new Card();
        Cash cash = new Cash();
        List<String> nomeMetodos = new ArrayList<>();
        ArrayAdapter<String> adapter;
        List<?> methods = BasePaymentMethod.queryAll();
        BasePaymentMethod payment;

        nomeMetodos.add("-");

        for(int i = 0; i < methods.size(); i++) {
            payment = (BasePaymentMethod) methods.get(i);
            nomeMetodos.add(payment.getName());
        }

        // Cria um ArrayAdapter usando a lista de contas cadastradas.
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nomeMetodos);
        // Especifica o layout a ser usado quando a lista aparece.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplica o adapter ao spinner.
        spinnerMetodos.setAdapter(adapter);

    }


    private boolean finish_transference(){
        if(areValuesSetCorrectly()){
            return true;
        }
        return false;
    }

    private boolean areValuesSetCorrectly(){
        String value = ((TextView)findViewById(R.id.edit_valor_transferencia)).getText().toString();
        if(value!=null && !value.isEmpty() && Double.parseDouble(value)>=0 &&
                ((Spinner)findViewById(R.id.spinner_contas_contabeis)).getSelectedItem().toString()!="-" &&
                ((Spinner)findViewById(R.id.spinner_contas_reais)).getSelectedItem().toString()!="-"){
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        if(v == botaoCancelar)
            redirecionaTela();
        else if(v == botaoSalvar) {
            if(areValuesSetCorrectly()) {
                BaseAccount contaOrigem, contaDestino;
                BasePaymentMethod method;
                try {
                    contaOrigem = (BaseAccount)BaseAccount.queryForField("name", spinnerContaOrigem.getSelectedItem().toString()).get(0);
                    contaDestino = (BaseAccount)BaseAccount.queryForField("name", spinnerContaDestino.getSelectedItem().toString()).get(0);
                    method = (BasePaymentMethod)BasePaymentMethod.queryForField("name", spinnerMetodos.getSelectedItem().toString()).get(0);
                    MoneyTransfer moneyTransfer = new MoneyTransfer(contaOrigem, contaDestino,
                            method, Float.parseFloat(valorTransferencia.getText().toString()), Utils.today());
                    moneyTransfer.save();
                    Toast toast = Toast.makeText(TransferMoney.this, "Transação salva com sucesso.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    redirecionaTela();
                } catch (SQLException e) {
                    Log.e("ERRO SQL", e.getMessage());
                }
            }
            else {
                Toast toast = Toast.makeText(TransferMoney.this, "Por favor, preencha todos os campos obrigatórios.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void redirecionaTela() {
        Intent intent;

        //if(intentExtra.equals("main")) {
            intent = new Intent(TransferMoney.this, MainActivity.class);
            startActivity(intent);
        //}
    }
}
