package com.example.gabrielcardoso.possogastar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

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
        LineChart chart = (LineChart) findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<>();
        //TODO pegar saldos dos meses passados
        entries.add(new Entry(1f,200f));
        entries.add(new Entry(2f,350f));
        entries.add(new Entry(3f,-30f));
        entries.add(new Entry(4f,200f));
        entries.add(new Entry(5f,100f));
        entries.add(new Entry(6f,500f));
        entries.add(new Entry(7f,650f));
        entries.add(new Entry(8f,450f));
        entries.add(new Entry(9f,500f));
        entries.add(new Entry(10f,475f));
        entries.add(new Entry(13f,300f));
        Toast.makeText(this, "Pressione sobre o grafico para dar zoom", Toast.LENGTH_LONG).show();
        //
        LineDataSet set = new LineDataSet(entries, "LineDataSet");
        LineData data = new LineData(set);
        chart.setData(data);
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
