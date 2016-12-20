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

import com.example.gabrielcardoso.possogastar.model.AccountingAccount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewAccountingAccount extends AppCompatActivity implements View.OnClickListener{
    EditText nomeConta, orcamento;
    Spinner spinner;
    Button botaoSalvar, botaoCancelar;
    String intentExtra, nomeTexto, orcamentoTexto;
    List<AccountingAccount> contasCadastradas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intentExtra = getIntent().getStringExtra("origem");

        nomeConta = (EditText) findViewById(R.id.nomeContaContabil);
        orcamento = (EditText) findViewById(R.id.budgetContaContabil);
        spinner = (Spinner) findViewById(R.id.contas);
        botaoSalvar = (Button) findViewById(R.id.salvar_conta_contabil);
        botaoCancelar = (Button) findViewById(R.id.cancelar_cadastro_conta_contabil);

        try {
            preencheSpinner(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        botaoSalvar.setOnClickListener(this);
        botaoCancelar.setOnClickListener(this);

    }

    public void preencheSpinner(Context context) throws SQLException {
        AccountingAccount account = new AccountingAccount();
        List<String> nomeContas = new ArrayList<>();
        ArrayAdapter<String> adapter;
        contasCadastradas = account.queryAll();

        nomeContas.add("-");

        for(int i = 0; i < contasCadastradas.size(); i++)
            nomeContas.add(contasCadastradas.get(i).getName());

        // Cria um ArrayAdapter usando a lista de contas cadastradas.
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nomeContas);
        // Especifica o layout a ser usado quando a lista aparece.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplica o adapter ao spinner.
        spinner.setAdapter(adapter);
    }

    public boolean camposObrigatoriosPreenchidos() {
        nomeTexto = nomeConta.getText().toString();
        orcamentoTexto = orcamento.getText().toString();

        if(nomeTexto != null && !nomeTexto.isEmpty() && !nomeTexto.equals(" ") &&
                orcamentoTexto != null && !orcamentoTexto.isEmpty() && Double.parseDouble(orcamentoTexto) >= 0)
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
                AccountingAccount accountingAccount;

                accountingAccount = inicializaConta(nomeTexto, Float.parseFloat(orcamentoTexto), spinner.getSelectedItem().toString());

                try {
                    accountingAccount.save();

                    Toast toast = Toast.makeText(NewAccountingAccount.this, "Conta salva com sucesso!",
                            Toast.LENGTH_SHORT);
                    toast.show();

                    redirecionaTela();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast toast = Toast.makeText(NewAccountingAccount.this, "Por favor, preencha todos os campos obrigatórios. Orçamento deve ser maior ou igual a zero.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    public void redirecionaTela() {
        Intent intent;

        if(intentExtra.equals("main")) {
            intent = new Intent(NewAccountingAccount.this, MainActivity.class);
            startActivity(intent);
        }
        else if(intentExtra.equals("manage")) {
            intent = new Intent(NewAccountingAccount.this, ManageAccounts.class);
            startActivity(intent);
        }
    }

    public AccountingAccount inicializaConta(String nomeConta, float orcamentoConta, String nomePai) {
        if(!nomePai.equals("-")) {
            for(int i = 0; i < contasCadastradas.size(); i++) {
                if(contasCadastradas.get(i).getName().equals(nomePai))
                    return new AccountingAccount(nomeConta, orcamentoConta, contasCadastradas.get(i));
            }
        }
        return new AccountingAccount(nomeConta, orcamentoConta);
    }
}
