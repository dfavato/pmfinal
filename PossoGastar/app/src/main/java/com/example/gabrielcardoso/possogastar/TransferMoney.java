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
            setAccountingAccountSpinner(this);
            setRealAccountSpinner(this);
            setPaymentMethodSpinner(this);
        }catch(SQLException e){
            Log.e("TransferMoney","SQL ERROR: "+e.toString());
        }

        botaoSalvar.setOnClickListener(this);
        botaoCancelar.setOnClickListener(this);

    }

    public void setRealAccountSpinner(Context context) throws SQLException {
        RealAccount account = new RealAccount();
        List<String> nomeContas = new ArrayList<>();
        ArrayAdapter<String> adapter;
        List<RealAccount>contasReaisCadastradas = account.queryAll();

        nomeContas.add("-");

        for(int i = 0; i < contasReaisCadastradas.size(); i++)
            nomeContas.add(contasReaisCadastradas.get(i).getName());

        // Cria um ArrayAdapter usando a lista de contas cadastradas.
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nomeContas);
        // Especifica o layout a ser usado quando a lista aparece.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplica o adapter ao spinner.
        ((Spinner)findViewById(R.id.spinner_contas_reais)).setAdapter(adapter);
    }

    public void setAccountingAccountSpinner(Context context) throws SQLException {
        AccountingAccount account = new AccountingAccount();
        List<String> nomeContas = new ArrayList<>();
        ArrayAdapter<String> adapter;
        List<AccountingAccount>contasContabeisCadastradas = account.queryAll();

        nomeContas.add("-");

        for(int i = 0; i < contasContabeisCadastradas.size(); i++)
            nomeContas.add(contasContabeisCadastradas.get(i).getName());

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
        List<Card> cardList = card.queryAll();
        List<Cash> cashList = cash.queryAll();

        nomeMetodos.add("-");

        for(int i = 0; i < cardList.size(); i++)
            nomeMetodos.add("Cartão - " + cardList.get(i).getName());
        for(int i = 0; i < cashList.size(); i++)
            nomeMetodos.add("Dinheiro - " + cashList.get(i).getName());

        // Cria um ArrayAdapter usando a lista de contas cadastradas.
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nomeMetodos);
        // Especifica o layout a ser usado quando a lista aparece.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplica o adapter ao spinner.
        spinnerMetodos.setAdapter(adapter);

    }

    /**
    private boolean finish_transference(){
        if(areValuesSetCorrectly()){
            return true;
        }
        return false;
    }*/

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
                contaOrigem = new BaseAccount(spinnerContaOrigem.getSelectedItem().toString());
                contaDestino = new BaseAccount(spinnerContaDestino.getSelectedItem().toString());
                //MoneyTransfer moneyTransfer = new MoneyTransfer();
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
