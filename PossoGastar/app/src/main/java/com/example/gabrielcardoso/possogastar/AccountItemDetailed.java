package com.example.gabrielcardoso.possogastar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gabrielcardoso.possogastar.model.BaseAccount;
import com.example.gabrielcardoso.possogastar.model.MoneyTransfer;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        try {
            BaseAccount acc = BaseAccount.queryForId(this.mAccountId);
            this.mAccountTitle = acc.getName();
            if(acc.lastUsed()==null){
                this.mLastUse = "Nunca";
            }else {
                this.mLastUse  = DateFormat.format("dd/MM/yy", acc.lastUsed()).toString();
            }
            this.mAmount = acc.saldo(Utils.today());
        } catch (SQLException e) {
            Log.e("SQL ERRO", e.getMessage());
        }

        setChart();
        displayAccountInfo();
        displayLastTransitions();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //encerra esta atividade quando aperta voltar
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setChart(){
        List<Entry> entries = new ArrayList<>();
        try {
            List<Float[]> saldos = BaseAccount.queryForId(this.mAccountId).saldosDiarios(Utils.today(), 7);
            for(Float[] p: saldos) {
                entries.add(new Entry(p[0], p[1]));
            }
        } catch (SQLException e) {
            Log.e("ERRO SQL", e.getMessage());
        }
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

    public ArrayList<BankTransition> getLastTransitions() {
        ArrayList<BankTransition> bankTransitions = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.DATE, -30);
        Date minus30days = cal.getTime();
        try {
            List<MoneyTransfer> transfers = BaseAccount.queryForId(this.mAccountId).statement();
            for(MoneyTransfer t: transfers) {
                bankTransitions.add(new BankTransition(t, this.mAccountId));
            }
        } catch (SQLException e) {
            Log.e("ERRO SQL", e.getMessage());
        }
        return bankTransitions;
    }
}
