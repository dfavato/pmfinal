package com.example.gabrielcardoso.possogastar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class AccountItemDetailed extends AppCompatActivity {
    //constantes
    public static String ACCOUNT_ID = "account_id";

    //detalhes das contas
    private int mAccountId = -1;
    private String mAccountTitle = "Placeholder";
    private double mAmount = -1;
    private String mLastUse = "Pl/ace/holder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_item_detailed);
        //
        Intent comming = getIntent();
        if(comming==null){
            finish();
        }
        this.mAccountId = Integer.valueOf(comming.getStringExtra(ACCOUNT_ID));
        //TODO pesquisar dados da conta no banco de dados
        //
        setChart();
        displayAccountInfo();
        displayLastTransitions();
    }

    public void setChart(){
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(2f,50f));
        entries.add(new Entry(3f,58f));
        entries.add(new Entry(5f,26.5f));
        entries.add(new Entry(8f,36f));
        entries.add(new Entry(9f,-18f));
        entries.add(new Entry(11f,22f));
        LineDataSet lineDataSet = new LineDataSet(entries,"Saldo");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.invalidate();
    }

    public void displayAccountInfo(){
        ((TextView)findViewById(R.id.account_title)).setText(this.mAccountTitle);
        ((TextView)findViewById(R.id.amount)).setText(String.valueOf(this.mAmount));
        ((TextView)findViewById(R.id.last_activity_date)).setText(this.mLastUse);
    }

    public void displayLastTransitions(){
        ListView listView = (ListView) findViewById(R.id.list_view);
        ArrayList<BankTransition> transitions = getLastTransitions();
        BankTransitionAdapter transitionAdapter = new BankTransitionAdapter(this,transitions);
        listView.setAdapter(transitionAdapter);
    }

    public ArrayList<BankTransition> getLastTransitions(){
        //TODO pegar as ultimas transações envolvendo essa conta no banco de dados
        ArrayList<BankTransition> bankTransitions = new ArrayList<>();
        bankTransitions.add(new BankTransition("01/06/2016","08:00",15,"Lanchonete da faculdade",mAccountId,"Carteira",8.50,mAccountId));
        bankTransitions.add(new BankTransition("03/06/2016","11:00",mAccountId,"Carteira",15,"conta banco do brasil",50.00,mAccountId));
        bankTransitions.add(new BankTransition("05/06/2016","18:00",17,"Gasolina",mAccountId,"Carteira",50,mAccountId));
        bankTransitions.add(new BankTransition("09/06/2016","12:30",mAccountId,"Carteira",15,"conta banco do brasil",70.00,mAccountId));
        return bankTransitions;
    }
}
