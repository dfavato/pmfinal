package com.example.gabrielcardoso.possogastar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Gabriel ==
 * Cardoso on 26/10/2016.
 */

public class AccountItemAdapter extends ArrayAdapter {
    private int mNumElements;
    public AccountItemAdapter(Context context, ArrayList<AccountItem> accounts){
        super(context, 0, accounts);
        this.mNumElements = accounts.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.account_list_item, parent, false);
        }
        AccountItem item = (AccountItem) getItem(position);
        TextView valueView = (TextView) listItemView.findViewById(R.id.value);
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        TextView dateView = (TextView) listItemView.findViewById(R.id.last_mov);
        valueView.setText(  String.valueOf(item.getmAmount()) );
        titleView.setText(item.getmTitle());
        if(item.getmLastUse() != null)
            dateView.setText(DateFormat.format("dd/MM", item.getmLastUse()));
        if(item.getmAmount()>0.0) valueView.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
        else valueView.setTextColor(parent.getResources().getColor(R.color.colorAccent));
        //alterar valores do item
        //
        //se for o ultimo item, ele deve ficar invisivel. Serve pra poder scrollar o ultimo item da lista
        //pra cima do botao flutuante, q de outra maneira o esconderia. (é um item em branco adicionado para este fim)
        if(item.getmVisibility()==View.INVISIBLE)
            listItemView.setVisibility(View.INVISIBLE);
        else
            listItemView.setVisibility(View.VISIBLE);
        return listItemView;
    }

    public static void setAccountItemList(Integer listId, ArrayList<AccountItem> items, Context context, Activity activity){
        //adaptados do array de contas para a ListView
        AccountItemAdapter accountAdapter = new AccountItemAdapter(context, items);
        ListView accountList = (ListView)activity.findViewById(listId);
        accountList.setAdapter(accountAdapter);
        accountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent accountItemDetailed = new Intent(context, AccountItemDetailed.class);
                accountItemDetailed.putExtra(AccountItemDetailed.ACCOUNT_ID, String.valueOf(items.get(position).getmId()));
                context.startActivity(accountItemDetailed);
            }
        });
    }
}
