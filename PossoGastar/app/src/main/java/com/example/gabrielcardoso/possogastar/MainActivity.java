package com.example.gabrielcardoso.possogastar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionMenu menuOpcoes;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    /**
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });**/



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setChart();
        setAccountList();



        /*floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent facebookIntent = getOpenFacebookIntent(MainActivity.this);
                startActivity(facebookIntent);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Intent twitterIntent = getOpenTwitterIntent(MainActivity.this);
                startActivity(twitterIntent);

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                Intent linkdinIntent = getOpenLinkdinIntent(MainActivity.this);
                startActivity(linkdinIntent);
            }
        });*/
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
        //TODO futuramente estes dados vão ser buscados do banco de dados:
        entries.add(new PieEntry(18.5f, "Comida"));
        entries.add(new PieEntry(26.7f, "Transporte"));
        entries.add(new PieEntry(24.0f, "Xerox"));
        entries.add(new PieEntry(30.8f, "Pao de batata"));
        //
        configChart(pieChart);
        PieDataSet set = new PieDataSet(entries, "Gastos do mês");
        set.setColors(ColorTemplate.MATERIAL_COLORS

        );
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }
    //
    public void configChart(PieChart piechart){
        piechart.setNoDataText("Ainda não há dados disponíveis");
        piechart.setHoleRadius(10f);
        piechart.setTransparentCircleRadius(15f);
        Description chartDescription = new Description();
        chartDescription.setEnabled(false);
        piechart.setDescription( chartDescription);
    }

    public void setAccountList(){
        //array que contem os dados das contasr
        final ArrayList<AccountItem> accounts = new ArrayList<>();
        //TODO elementos futuramente serão adicionados dinamicamente
        accounts.add(new AccountItem("Titulo",-25.0,new Date(),1));
        accounts.add(new AccountItem("Titulo",25.5,new Date(),2));
        accounts.add(new AccountItem("Titulo",75.0,new Date(),3));
        //criando uma conta a mais que serve para scroolar o ultimo elemento, que pode vir a ficar
        //escondido sobre o botao flutuando
        accounts.add(new AccountItem("",00.0,new Date(),-1,View.INVISIBLE));
        //adaptados do array de contas para a ListView
        AccountItemAdapter accountAdapter = new AccountItemAdapter(this,accounts);
        ListView accountList = (ListView)findViewById(R.id.account_list);
        accountList.setAdapter(accountAdapter);
        accountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent accountItemDetailed = new Intent(getApplicationContext(), AccountItemDetailed.class);
                accountItemDetailed.putExtra(AccountItemDetailed.ACCOUNT_ID, String.valueOf(accounts.get(position).getmId()));
                startActivity(accountItemDetailed);
            }
        });

    }

    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/376227335860239")); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/karthikofficialpage")); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenTwitterIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.twitter.android", 0); //Checks if Twitter is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/drkarthiik")); //Trys to make intent with Twitter's's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/drkarthiik")); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenLinkdinIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.linkedin.android", 0); //Checks if Linkdin is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/in/karthikm128")); //Trys to make intent with Linkdin's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/in/karthikm128")); //catches and opens a url to the desired page
        }
    }

    /**
     * Created by Gabriel Cardoso on 21/11/2016.
     *
     *
     */
}
//TODO listas de coisas pra fazer, sendo feitas
/*
-> operações de transferencia (tela etc)
-> gerenciar contas (mostras lista das contas, poder adicionar e remover)  (comecei a fazer (GABRIEL))
-> extrato, detalhes de contas (estou fazendo (GABRIEL))
-> funcionamento do banco de dados (estou fazendo(DANILO))
*/
