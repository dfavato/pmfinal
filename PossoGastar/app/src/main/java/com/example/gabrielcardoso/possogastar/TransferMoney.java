package com.example.gabrielcardoso.possogastar;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gabrielcardoso.possogastar.model.AccountingAccount;
import com.example.gabrielcardoso.possogastar.model.RealAccount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransferMoney extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);
        findViewById(R.id.finalizar_transferencia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finish_transference()) finish();
            }
        });
        try{
            setAccountingAccountSpinner(this);
            setRealAccountSpinner(this);
        }catch(SQLException e){
            Log.e("TransferMoney","SQL ERROR: "+e.toString());
        }

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

    private boolean finish_transference(){
        if(areValuesSetCorrectly()){
        }
        return true;
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
}
