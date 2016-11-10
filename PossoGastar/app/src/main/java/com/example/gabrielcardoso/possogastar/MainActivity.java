package com.example.gabrielcardoso.possogastar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setChart();
        setAccountList();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.transfer_money) {
            Intent newWindow = new Intent(MainActivity.this, TransferMoney.class);
            startActivity(newWindow);
        } else if (id == R.id.bank_statement) {
            Intent newWindow = new Intent(MainActivity.this, BankStatement.class);
            startActivity(newWindow);
        } else if (id == R.id.manage_accounts) {
            Intent newWindow = new Intent(MainActivity.this, ManageAccounts.class);
            startActivity(newWindow);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //
    public void setChart(){
        //Preenchendo o gráfico com dados do mês e escondendo placeholders
        //localizando view que contém o gráfico
        PieChart pieChart = (PieChart) findViewById(R.id.pie_chart);
        //criando array que conterá informações do gráfico
        List<PieEntry> entries = new ArrayList<>();
        //futuramente estes dados vão ser buscados do banco de dados:
        entries.add(new PieEntry(18.5f, "Comida"));
        entries.add(new PieEntry(26.7f, "Transporte"));
        entries.add(new PieEntry(24.0f, "Xerox"));
        entries.add(new PieEntry(30.8f, "Pao de batata"));
        //
        if(entries.size()!=0){
            findViewById(R.id.chart_placeholder).setVisibility(View.GONE);
            pieChart.setVisibility(View.VISIBLE);
            //
            PieDataSet set = new PieDataSet(entries, "Gastos do mês");
            set.setColors(ColorTemplate.VORDIPLOM_COLORS);
            PieData data = new PieData(set);
            pieChart.setData(data);
            pieChart.invalidate(); // refresh
        }
    }
    public void setAccountList(){
        //array que contem os dados das contas
        final ArrayList<AccountItem> accounts = new ArrayList<>();
        //elementos futuramente serão adicionados dinamicamente
        accounts.add(new AccountItem("Titulo",-25.0,new Date()));
        accounts.add(new AccountItem("Titulo",25.5,new Date()));
        accounts.add(new AccountItem("Titulo",75.0,new Date()));
        //criando uma conta a mais que serve para scroolar o ultimo elemento, que pode vir a ficar
        //escondido sobre o botao flutuando
        accounts.add(new AccountItem("",00.0,new Date(),View.INVISIBLE));
        //adaptados do array de contas para a ListView
        AccountItemAdapter accountAdapter = new AccountItemAdapter(this,accounts);
        ListView accountList = (ListView)findViewById(R.id.account_list);
        accountList.setAdapter(accountAdapter);

    }
}