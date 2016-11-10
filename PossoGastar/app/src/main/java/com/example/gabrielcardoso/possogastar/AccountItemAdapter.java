package com.example.gabrielcardoso.possogastar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        valueView.setText(  String.valueOf(item.getmAmount()) );
        if(item.getmAmount()>0.0) valueView.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
        else valueView.setTextColor(parent.getResources().getColor(R.color.colorAccent));
        //alterar valores do item
        //
        //se for o ultimo item, ele deve ficar invisivel. Serve pra poder scrollar o ultimo item da lista
        //pra cima do botao flutuante, q de outra maneira o esconderia. (eé um item em branco adicionado para este fim)
        if(item.getmVisibility()==View.INVISIBLE)
            listItemView.setVisibility(View.INVISIBLE);
        else
            listItemView.setVisibility(View.VISIBLE);
        return listItemView;
    }
}
