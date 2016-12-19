package com.example.gabrielcardoso.possogastar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gabrielcardoso.possogastar.model.PaymentMethod;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NewPaymentMethod extends AppCompatActivity implements View.OnClickListener{
    Spinner spinnerTipo;
    EditText nomeMetodoPagamento, limiteMetodoPagamento, vencimentoMetodoPagamento, fechamentoMetodoPagamento;
    Button botaoSalvar, botaoCancelar;
    String intentExtra, nomeTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_payment_method);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //intentExtra = getIntent().getStringExtra("origem");

        nomeMetodoPagamento = (EditText) findViewById(R.id.nomeMetodoPagamento);
        limiteMetodoPagamento = (EditText) findViewById(R.id.limiteMetodoPagamento);
        vencimentoMetodoPagamento = (EditText) findViewById(R.id.vencimentoMetodoPagamento);
        fechamentoMetodoPagamento = (EditText) findViewById(R.id.fechamentoMetodoPagamento);
        spinnerTipo = (Spinner) findViewById(R.id.tiposMetodoPagamento);

        botaoSalvar = (Button) findViewById(R.id.salvarMetodoPagamento);
        botaoCancelar = (Button) findViewById(R.id.cancelarCadastroMetodoPagamento);

        try {
            preencheSpinnerTipo(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        botaoSalvar.setOnClickListener(this);
        botaoCancelar.setOnClickListener(this);

    }
    public void preencheSpinnerTipo(Context context) throws SQLException {

        List<String> nomeTipos = new ArrayList<>();
        nomeTipos.add("CASH");
        nomeTipos.add("CARD");
        ArrayAdapter<String> adapter;

        // Cria um ArrayAdapter usando a lista de tipos.
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nomeTipos);
        // Especifica o layout a ser usado quando a lista aparece.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplica o adapter ao spinner.
        spinnerTipo.setAdapter(adapter);
    }

    public boolean camposObrigatoriosPreenchidos() {
        nomeTexto = nomeMetodoPagamento.getText().toString();

        if(nomeTexto != null && !nomeTexto.isEmpty() && !nomeTexto.equals(" "))
            return true;

        return false;
    }

    @Override
    public void onClick(View v) {

        if(v == botaoCancelar) {
            finish();
            //redirecionaTela();
        }
        else if(v == botaoSalvar) {
            if(camposObrigatoriosPreenchidos()) {
                PaymentMethod paymentMethod;

                int vencimentoInt = Integer.parseInt(vencimentoMetodoPagamento.getText().toString());
                byte vencimentoByte = (byte)vencimentoInt;

                int fechamentoInt = Integer.parseInt(fechamentoMetodoPagamento.getText().toString());
                byte fechamentoByte = (byte)fechamentoInt;

                paymentMethod = inicializaMetodoPagamento(spinnerTipo.getSelectedItem().toString(),
                        nomeTexto, Float.parseFloat(limiteMetodoPagamento.getText().toString()),
                        vencimentoByte,
                        fechamentoByte);

                try {
                    paymentMethod.save();

                    Toast toast = Toast.makeText(NewPaymentMethod.this, "Metodo de Pagamento salvo com sucesso!",
                            Toast.LENGTH_SHORT);
                    toast.show();

                    finish();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast toast = Toast.makeText(NewPaymentMethod.this, "Por favor, preencha todos os campos obrigatórios.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void redirecionaTela() {
        Intent intent;

        if(intentExtra.equals("main")) {
            intent = new Intent(NewPaymentMethod.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public PaymentMethod inicializaMetodoPagamento(String tipo, String nomeMetodo, float limite, byte vencimento, byte fechamento) {

        return new PaymentMethod(nomeMetodo, tipo, limite, vencimento, fechamento);
    }



}
