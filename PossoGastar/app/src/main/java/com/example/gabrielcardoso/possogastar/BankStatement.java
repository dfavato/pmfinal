package com.example.gabrielcardoso.possogastar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class BankStatement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_statement);
        setChart();
        setBankTransitionList();
    }

    public void setChart(){
        BarChart chart = (BarChart) findViewById(R.id.chart);
        List<BarEntry> entries = new ArrayList<>();
        //TODO pegar saldos dos meses passados
        entries.add(new BarEntry(1f,200f));
        entries.add(new BarEntry(2f,350f));
        entries.add(new BarEntry(3f,-30f));
        entries.add(new BarEntry(4f,200f));
        entries.add(new BarEntry(5f,100f));
        entries.add(new BarEntry(6f,500f));
        entries.add(new BarEntry(7f,650f));
        //
        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        chart.setData(data);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh
    }

    public void setBankTransitionList(){
        ListView listView = (ListView) findViewById(R.id.list_view);
        ArrayList<BankTransition> bankTransitions = new ArrayList<>();
        //TODO pesquisas transações no banco de dados e remover estes placeholders
        bankTransitions.add(new BankTransition("01/06/2016","08:00",15,"Lanchonete da faculdade",18,"Carteira",8.50));
        bankTransitions.add(new BankTransition("03/06/2016","11:00",18,"Carteira",15,"conta banco do brasil",50.00));
        bankTransitions.add(new BankTransition("05/06/2016","18:00",17,"Gasolina",18,"Carteira",50));
        bankTransitions.add(new BankTransition("09/06/2016","12:30",18,"Carteira",15,"conta banco do brasil",70.00));
        bankTransitions.add(new BankTransition("11/06/2016","18:00",17,"Lanchonete da faculdade",18,"Carteira",50));
        bankTransitions.add(new BankTransition("24/06/2016","19:00",17,"Bar no fds",18,"Carteira",150));
        bankTransitions.add(new BankTransition("26/06/2016","10:00",17,"Gasolina",18,"Carteira",100));
        bankTransitions.add(new BankTransition("01/07/2016","12:00",17,"Lanchonete",18,"Carteira",8));
        //
        BankTransitionAdapter bankTransitionAdapter = new BankTransitionAdapter(this,bankTransitions);
        listView.setAdapter(bankTransitionAdapter);
    }
}
