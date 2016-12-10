package com.example.gabrielcardoso.possogastar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    public ArrayList<BankTransition> getLastTransitions() {
        ArrayList<BankTransition> bankTransitions = new ArrayList<>();
        try {
            BaseAccount acc = BaseAccount.queryForId(this.mAccountId);
            List<MoneyTransfer> transfers = MoneyTransfer.queryAllForAccount(acc);
            for(MoneyTransfer t: transfers) {
                bankTransitions.add(new BankTransition(t.getFormatedPaymentDate("dd/MM/yyyy"),
                        "00:00", t.getOrigin().getId(), t.getOrigin().getName(),
                        t.getDestiny().getId(), t.getDestiny().getName(), t.getValue(), this.mAccountId));
            }
        } catch (SQLException e) {
            Log.e("ERRO SQL", e.getMessage());
        }
        return bankTransitions;
    }
}
